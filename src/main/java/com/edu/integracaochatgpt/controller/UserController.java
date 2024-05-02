package com.edu.integracaochatgpt.controller;

import com.edu.integracaochatgpt.domain.User;
import com.edu.integracaochatgpt.dto.AuthenticationDTO;
import com.edu.integracaochatgpt.dto.LoginResponseDTO;
import com.edu.integracaochatgpt.dto.RegisterDTO;
import com.edu.integracaochatgpt.dto.UserDTO;
import com.edu.integracaochatgpt.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
        ResponseEntity<LoginResponseDTO> loginResponseEntity = userService.login(authenticationDTO);
        if (loginResponseEntity.getStatusCode() == HttpStatus.OK) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", loginResponseEntity.getBody().getToken());
            return ResponseEntity.ok().headers(headers).body(loginResponseEntity.getBody());
        }
        return loginResponseEntity;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDTO registerDTO) {
        userService.registerUser(registerDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listarUsuarios() {
        List<UserDTO> userDTOs = userService.listarUsuarios();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}
