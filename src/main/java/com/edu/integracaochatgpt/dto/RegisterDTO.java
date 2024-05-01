package com.edu.integracaochatgpt.dto;



import com.edu.integracaochatgpt.domain.UserRole;
import jakarta.validation.constraints.NotNull;


public record RegisterDTO (@NotNull String login, @NotNull String password, @NotNull UserRole role) {

}
