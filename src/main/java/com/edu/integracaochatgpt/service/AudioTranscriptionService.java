package com.edu.integracaochatgpt.service;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AudioTranscriptionService {

    private final SpeechClient speechClient;

    @Autowired
    public AudioTranscriptionService(SpeechClient speechClient) {
        this.speechClient = speechClient;
    }

    public String transcreverAudio(MultipartFile arquivoAudio) throws IOException {
        try {
            byte[] audioBytes = arquivoAudio.getBytes();
            ByteString audioBytesString = ByteString.copyFrom(audioBytes);

            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                            .setSampleRateHertz(16000)
                            .setLanguageCode("pt-BR")
                            .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytesString).build();

            RecognizeResponse response = speechClient.recognize(config, audio);

            StringBuilder transcripcao = new StringBuilder();
            List<SpeechRecognitionResult> results = response.getResultsList();
            for (SpeechRecognitionResult result : results) {
                transcripcao.append(result.getAlternativesList().get(0).getTranscript());
            }
            return transcripcao.toString();
        } catch (ApiException e) {
            System.out.println("Erro ao transcrever áudio: " + e.getStatusCode().getCode());
            throw e;
        }
    }
}
