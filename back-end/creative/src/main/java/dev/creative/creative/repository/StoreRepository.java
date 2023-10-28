package dev.creative.creative.repository;

import dev.creative.creative.entity.StorageEntity;
import dev.creative.creative.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Jpa를 통해 데이터베이스에 INSERT, UPDATE, DELETE, QUERY작업을 수행
@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    // SELECT * FROM store WHERE coordinate_x BETWEEN (?, ?) AND coordinate_y BETWEEN (?, ?)
    public List<StoreEntity> getStoreEntitiesByCoordinateXBetweenAndCoordinateYBetween(Double minX,  Double maxX, Double minY, Double maxY);

    public StoreEntity getStoreEntityByEmail(String email);
    public boolean existsByEmail(String email);
}
