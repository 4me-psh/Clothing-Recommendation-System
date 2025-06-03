package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.person.PersonEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

@Lazy
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    PersonEntity findByUserEntity_Id(Long userId);
}
