package org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql;

import org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
    List<RecommendationEntity> findAllByPersonEntityId(Long id);
    List<RecommendationEntity> findAllByPersonEntityIdAndFavorite(Long id, Boolean favorite);
    @Transactional
    @Modifying
    @Query("""
        delete from RecommendationEntity r
        where exists (
            select pc.id
            from r.recommendedClothesEntities pc
            where pc.id = :pieceId
        )
    """)
    void deleteAllByPieceId(Long pieceId);

    @Query("""
    select r from RecommendationEntity r
    join r.recommendedClothesEntities pc
    where pc.id = :pieceId
""")
    List<RecommendationEntity> findAllByPieceId(Long pieceId);

}
