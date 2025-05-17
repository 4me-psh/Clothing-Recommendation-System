package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.user.UserEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

@Lazy
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
