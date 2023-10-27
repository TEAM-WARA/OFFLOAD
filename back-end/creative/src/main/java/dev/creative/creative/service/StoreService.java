package dev.creative.creative.service;


import dev.creative.creative.dto.RangeDTO;
import dev.creative.creative.dto.StoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// 상점 정보 관련 비즈니스 로직 인터페이스
public interface StoreService {
    // 상점 정보 생성
    public ResponseEntity<StoreDTO> createStore(StoreDTO storeDTO, List<MultipartFile> images) throws IOException;
    // 상점 정보 id기반 조회
    public ResponseEntity<StoreDTO> readStore(Long id);
    // 상점 정보 전체 조회
    public ResponseEntity<List<StoreDTO>> readAllStore();
    // 상점 정보 좌표기준 내의 값 전체 조회
    public ResponseEntity<List<StoreDTO>> readAllStoreBetweenSquare(RangeDTO rangeDTO);
    // 상점 정보 수정
    public ResponseEntity<StoreDTO> updateStore(Long id, StoreDTO storeDTO);
    // 상점 정보 삭제
    public ResponseEntity<Boolean> deleteStore(Long id);


}
