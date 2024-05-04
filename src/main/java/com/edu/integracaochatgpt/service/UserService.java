package com.edu.integracaochatgpt.service;

import com.edu.integracaochatgpt.domain.User;
import com.edu.integracaochatgpt.dto.AuthenticationDTO;
import com.edu.integracaochatgpt.dto.LoginResponseDTO;
import com.edu.integracaochatgpt.dto.RegisterDTO;
import com.edu.integracaochatgpt.dto.UserDTO;
import com.edu.integracaochatgpt.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ApplicationContext context;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<LoginResponseDTO> login(AuthenticationDTO data) {
        try {
            AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
            UserDetails userDetails = loadUserByUsername(data.username());
            var userNamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = authenticationManager.authenticate(userNamePassword);
            SecurityContextHolder.getContext().setAuthentication(auth);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token.getToken(), token.getExpiration());
            return ResponseEntity.ok(loginResponseDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO("Usuário não encontrado", null));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO("Credenciais inválidas", null));
        }
    }

    public void registerUser(RegisterDTO registerDTO) {
        // Verificando se o nome de usuário já está em uso
        if (userRepository.findByUsername(registerDTO.username()) != null) {
            throw new IllegalArgumentException("Nome de usuário já está em uso");
        }
        if (registerDTO.password() == null || registerDTO.password().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser vazia");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.username(), encryptedPassword, registerDTO.email(), registerDTO.role());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        userRepository.save(newUser);
    }

    public List<UserDTO> listarUsuarios() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(id, "ID: " + id + " Not Found"));
    }
}
