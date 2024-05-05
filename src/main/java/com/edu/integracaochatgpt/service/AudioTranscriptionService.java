package com.edu.integracaochatgpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AudioTranscriptionService {

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String transcribeAudio(MultipartFile audioFile, String token, String apiKey) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Bearer " + token);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("audio", audioFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                openAiApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to transcribe audio: " + response.getStatusCode());
        }
    }
}
