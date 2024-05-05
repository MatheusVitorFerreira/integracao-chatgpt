package com.edu.integracaochatgpt.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Upload {

    private MultipartFile audio;
    private String msg;
}
