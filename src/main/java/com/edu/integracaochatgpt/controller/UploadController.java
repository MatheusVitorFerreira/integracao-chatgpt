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
            if (audioFile == null || audioFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Arquivo de áudio não fornecido.");
            }

            String transcription = transcriptionService.transcribeAudio(audioFile);

            // Verifica se a transcrição foi bem-sucedida
            if (transcription != null && !transcription.isEmpty()) {
                return ResponseEntity.ok(transcription);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível transcrever o áudio.");
            }
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao transcrever áudio.");
        }
    }
}