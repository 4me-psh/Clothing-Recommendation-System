package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage.GeneratedImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneratedImageRepository extends JpaRepository<GeneratedImageEntity, Long> {
}
