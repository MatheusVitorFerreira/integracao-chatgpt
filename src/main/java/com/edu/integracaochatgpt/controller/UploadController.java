package com.edu.integracaochatgpt.controller;

import com.edu.integracaochatgpt.service.AudioTranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/audio")
public class UploadController {

    @Autowired
    private AudioTranscriptionService audioTranscriptionService;

    @PostMapping("/transcribe")
    public ResponseEntity<String> transcribeAudio(@RequestParam("audio") MultipartFile audioFile, @RequestHeader("Authorization") String token, @RequestHeader("x-api-key") String apiKey) {
        try {
            String transcription = audioTranscriptionService.transcribeAudio(audioFile, token, apiKey);
            return ResponseEntity.ok(transcription);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to transcribe audio: " + e.getMessage());
        }
    }
}
