package dev.creative.creative.repository;

import dev.creative.creative.entity.StorageEntity;
import dev.creative.creative.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
    public List<StoreEntity> getStoreEntitiesByCoordinateXBetweenAndCoordinateYBetween(Double minX,  Double maxX, Double minY, Double maxY);
}
