package com.edu.integracaochatgpt.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Service
public class AudioTranscriptionService {
    @Autowired
    private ResourceLoader resourceLoader;

    public String transcribeAudio(MultipartFile audioFile) {
        try {

            Resource resource = resourceLoader.getResource("classpath:transcrever-audio-em-texto-b8708667f6a3.json");
            InputStream inputStream = resource.getInputStream();
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);

            SpeechSettings settings = SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SpeechClient speechClient = SpeechClient.create(settings)) {

                byte[] audioBytes = audioFile.getBytes();

                RecognitionConfig config =
                        RecognitionConfig.newBuilder()
                                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                                .setSampleRateHertz(16000)
                                .setLanguageCode("pt-BR")
                                .build();

                RecognitionAudio audio = RecognitionAudio.newBuilder()
                        .setContent(com.google.protobuf.ByteString.copyFrom(audioBytes))
                        .build();

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
