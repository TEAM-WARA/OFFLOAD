package dev.creative.creative.dao;

import dev.creative.creative.entity.StorageEntity;

import java.util.List;

// 물품 보관 요청서 Data Acess Object 인터페이스
public interface StorageDAO {
    // 물품 보관 요청서 생성
    public StorageEntity createStorage(StorageEntity storageEntity);
    // 물품 보관 요청서 id기반 조회
    public StorageEntity readStorage(Long id);
    // 물품 보관 요청서 전체 조회
    public List<StorageEntity> readAllStorage();
    // 물품 보관 요청서 관리자기반 조회
    public List<StorageEntity> readAllStorageByStoreEmail(String email);
    // 물품 보관 요청서 유저기반 조회
    public List<StorageEntity> readAllStorageByEmail(String email);
    // 물품 보관 요청서 삭제
    public Boolean deleteStorage(Long id);
    // 승락된 물품 보관 요청서 관리자 기반 조회
    public List<StorageEntity> readAllStorageByEmailAndAllow(String email);

}
