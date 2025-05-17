package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes.PieceOfClothesEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Lazy
public interface PieceOfClothesRepository extends JpaRepository<PieceOfClothesEntity, Long> {
    List<PieceOfClothesEntity> findAllByUserEntity_Id(Long id);

}
