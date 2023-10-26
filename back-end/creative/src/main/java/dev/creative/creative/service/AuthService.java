package dev.creative.creative.service;


import dev.creative.creative.dto.TokenDTO;
import dev.creative.creative.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<UserDTO> signUp(UserDTO userDTO);

    ResponseEntity<TokenDTO> signIn(UserDTO userDTO);
    public boolean emailDuplicateCheck(String email);
    public boolean tokenValidCheck(String token);
}