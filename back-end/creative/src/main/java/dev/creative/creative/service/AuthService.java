package dev.creative.creative.service;


import dev.creative.creative.dto.TokenDTO;
import dev.creative.creative.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

// 인증 및 인가와 관련된 비즈니스 로직
public interface AuthService {

    // 회원가입
    public ResponseEntity<UserDTO> signUp(UserDTO userDTO);
    // 로그인
    public ResponseEntity<TokenDTO> signIn(UserDTO userDTO);
    // 이메일 중복 체크
    public boolean emailDuplicateCheck(String email);
    // 토큰 검증
    public boolean tokenValidCheck(String token);
}