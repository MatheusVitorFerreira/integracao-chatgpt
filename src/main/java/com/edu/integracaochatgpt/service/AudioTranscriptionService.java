package com.edu.integracaochatgpt.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AudioTranscriptionService {

    @Value("${google.cloud.speech.api-key}")
    private String apiKey;

    public String transcribeAudio(MultipartFile audioFile) {
        try {
            // Configure the credentials provider
            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(new FileInputStream("path/to/serviceAccountKey.json"));
            SpeechSettings settings = SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            // Create the SpeechClient with the configured settings
            try (SpeechClient speechClient = SpeechClient.create(settings)) {

                // Convert the MultipartFile to bytes
                byte[] audioBytes = audioFile.getBytes();

                // Configure the recognition request
                RecognitionConfig config =
                        RecognitionConfig.newBuilder()
                                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                .setSampleRateHertz(16000)
                                .setLanguageCode("pt-BR")
                                .build();

                RecognitionAudio audio = RecognitionAudio.newBuilder()
                        .setContent(com.google.protobuf.ByteString.copyFrom(audioBytes))
                        .build();

                // Perform the speech recognition request
                RecognizeResponse response = speechClient.recognize(config, audio);
                List<SpeechRecognitionResult> results = response.getResultsList();

                StringBuilder transcription = new StringBuilder();
                for (SpeechRecognitionResult result : results) {
                    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                    transcription.append(alternative.getTranscript());
                }

                return transcription.toString();
            }
        } catch (IOException | ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
