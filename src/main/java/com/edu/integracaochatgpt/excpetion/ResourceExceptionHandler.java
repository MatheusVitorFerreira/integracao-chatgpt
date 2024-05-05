package com.edu.integracaochatgpt.excpetion;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import com.edu.integracaochatgpt.excpetion.exceptions.TranscriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler(TranscriptionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> RomChatNotFoundException(TranscriptionException e, HttpServletRequest request) {

        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Erro na Transcrição",
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), "Access denied",
                System.currentTimeMillis());
        ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(err));

    }
}

