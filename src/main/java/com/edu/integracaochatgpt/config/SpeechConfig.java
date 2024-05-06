package com.edu.integracaochatgpt.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class SpeechConfig {

    @Bean
    public SpeechClient speechClient() throws IOException {
        FileInputStream credentialsStream = new FileInputStream("src/main/resources/transcrever-audio-em-texto-b8708667f6a3.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        SpeechSettings settings = SpeechSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
        return SpeechClient.create(settings);
    }
}

