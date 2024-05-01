package com.edu.integracaochatgpt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

}
