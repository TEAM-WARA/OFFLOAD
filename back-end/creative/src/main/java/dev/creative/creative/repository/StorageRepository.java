package dev.creative.creative.repository;

import dev.creative.creative.entity.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    public List<StorageEntity> getAllByStoreEmail(String storeEmail);
    public List<StorageEntity> getAllByEmail(String email);
}
