package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
    List<RecommendationEntity> findAllByPersonEntityId(Long id);
}
