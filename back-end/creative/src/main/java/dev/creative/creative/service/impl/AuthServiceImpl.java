package dev.creative.creative.service.impl;


import dev.creative.creative.config.security.JwtTokenProvider;
import dev.creative.creative.dto.StoreDTO;
import dev.creative.creative.dto.TokenDTO;
import dev.creative.creative.dto.UserDTO;
import dev.creative.creative.entity.UserEntity;
import dev.creative.creative.repository.UserRepository;
import dev.creative.creative.service.AuthService;
import dev.creative.creative.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


// 로그인 관련 서비스 구현체
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;
    public final StoreService storeService;

    @Autowired
    public AuthServiceImpl(
            StoreService storeService,
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.storeService = storeService;
    }

    @Override
    public boolean emailDuplicateCheck(String email){
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean tokenValidCheck(String token) {
        return this.jwtTokenProvider.validateToken(token);
    }

    @Override
    public ResponseEntity<UserDTO> signUp(UserDTO userDTO) {
        logger.info("[getSignUpResult] 회원가입 정보 전달");
        UserEntity userEntity;

        // 이메일 중복 체크
        if(this.emailDuplicateCheck(userDTO.getEmail())){
            return ResponseEntity.status(400).body(UserDTO.builder().email("email already exists").build());
        }

        // 규칙 설정
        if(userDTO.getRole().equalsIgnoreCase("admin")){
            userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }
        else if(userDTO.getRole().equalsIgnoreCase("seller")){
            userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .roles(Collections.singletonList("ROLE_SELLER"))
                    .build();
        }
        else {
            userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        // 생성한 유저 엔티티를 DB에 저장
        UserEntity savedUser = userRepository.save(userEntity);

        logger.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과 값 주입");
        if(!savedUser.getEmail().isEmpty()){
            logger.info("[getSignUpResult] 정상 처리 완료");
            return ResponseEntity.status(201).body(UserDTO.builder().email(userDTO.getEmail()).build());
        } else{
            logger.info("[getSignUpResult]  실패 처리 완료");
            return ResponseEntity.badRequest().body(UserDTO.builder().email("email null").build());

        }

    }

    @Override
    public ResponseEntity<TokenDTO> signIn(UserDTO userDTO) {
        logger.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        UserEntity userEntity = userRepository.getByEmail(userDTO.getEmail());
        logger.info("[getSignInResult] e-mail : {}", userDTO.getEmail());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setEmail(userDTO.getEmail());
        tokenDTO.setRole(userEntity.getRoles().get(0).equals("ROLE_USER") ? "user" : "seller" );
        tokenDTO.setStoreId( this.storeService.readStoreByEmail(userDTO.getEmail()).getBody().getId());
        logger.info("[getSignInResult] 패스워드 비교 수행");
        try{
            try{// 패스워드 불일치
                if (!passwordEncoder.matches(userDTO.getPassword(), userEntity.getPassword())) {
                    logger.info("패스워드 불일치");
                    tokenDTO.setToken("Password mismatch");
                    return ResponseEntity.badRequest().body(tokenDTO);
                }
            }catch (IllegalArgumentException e){
                tokenDTO.setToken("Email mismatch");
                return ResponseEntity.badRequest().body(tokenDTO);
            }
        }catch (NullPointerException e){
            tokenDTO.setToken("Email Null");
            return ResponseEntity.badRequest().body(tokenDTO);
        }

        logger.info("[getSignInResult] 패스워드 일치");
        tokenDTO.setToken(jwtTokenProvider.createToken(String.valueOf(userEntity.getEmail()), userEntity.getRoles()));
        return ResponseEntity.status(200).body(tokenDTO);

    }

}
