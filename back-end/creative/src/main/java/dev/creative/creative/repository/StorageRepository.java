package dev.creative.creative.repository;

import dev.creative.creative.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Jpa를 통해 데이터베이스에 INSERT, UPDATE, DELETE, QUERY작업을 수행
@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    // SELECT * FROM storage WHERE store_email = ?
    public List<StorageEntity> getAllByStoreEmail(String storeEmail);
    // SELECT * FROM storage WHERE email = ?
    public List<StorageEntity> getAllByEmail(String email);
    // SELECT * FROM storage WHERE store_email = ? AND allow = 1
    public List<StorageEntity> getAllByStoreEmailAndAllow(String storeEmail, Boolean allow);
}
