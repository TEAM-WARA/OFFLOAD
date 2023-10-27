package dev.creative.creative.service;

import dev.creative.creative.dto.StorageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// 물품 보관 신청서 관련 비즈니스 로직
public interface StorageService {
    // 물품 보관 신청서 생성
    public ResponseEntity<StorageDTO> createStorage(StorageDTO storageDTO, List<MultipartFile> images) throws IOException;
    // 물품 보관 신청서 id기반 조회
    public ResponseEntity<StorageDTO> readStorage(Long id);
    // 물품 보관 신청서 전체 조회
    public ResponseEntity<List<StorageDTO>> readAllStorage();
    // 물품 보관 신청서 관리자 기반 조회
    public ResponseEntity<List<StorageDTO>> readAllStorageByStoreEmail(String email);
    // 물품 보관 신청서 유저 기반 조회
    public ResponseEntity<List<StorageDTO>> readAllStorageByEmail(String email);
    // 물품 보관 신청서 삭제
    public ResponseEntity<Boolean> deleteStorage(Long id);
    // 물품 보관 신청서 승락
    public ResponseEntity<StorageDTO> allowStorage(String email, Long id);
    // 물품 보관 신청서 거부
    public ResponseEntity<StorageDTO> deniedStorage(String email, Long id);
    // 승락된 물품 보관 신청서 관리자 기반 조회
    public ResponseEntity<List<StorageDTO>> readAllStorageByAllowed(String email);


}
