package com.edu.integracaochatgpt.controller;

import com.edu.integracaochatgpt.service.AudioTranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transcription")
public class UploadController {

    @Autowired
    private AudioTranscriptionService transcriptionService;

    @PostMapping("/audio")
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile audioFile) {
        try {
            String transcription = transcriptionService.transcribeAudio(audioFile);
            return ResponseEntity.ok(transcription);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao transcrever áudio.");
        }
    }
}
