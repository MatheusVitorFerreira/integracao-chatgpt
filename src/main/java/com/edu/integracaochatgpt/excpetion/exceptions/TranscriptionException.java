package com.edu.integracaochatgpt.excpetion.exceptions;

public class TranscriptionException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public TranscriptionException(String msg) {
        super(msg);
    }
}
