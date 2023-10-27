package dev.creative.creative.repository;


import dev.creative.creative.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Jpa를 통해 데이터베이스에 INSERT, UPDATE, DELETE, QUERY작업을 수행
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // SELECT * FROM user WHERE email = ?
    public UserEntity getByEmail(String email);
    public boolean existsByEmail(String email);
}
