package dev.creative.creative.repository;


import dev.creative.creative.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Jpa를 통해 데이터베이스에 INSERT, UPDATE, DELETE, QUERY작업을 수행
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
