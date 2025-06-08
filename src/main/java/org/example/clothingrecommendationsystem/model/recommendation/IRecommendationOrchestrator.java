package org.example.clothingrecommendationsystem.model.recommendation;

import org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation.RecommendationEntity;

import java.util.List;

public interface IRecommendationOrchestrator {
    List<Recommendation> getAll();
    Recommendation getById(Long id);
    Recommendation create(Recommendation entityToCreate);
    Recommendation edit(Recommendation entityToUpdate);
    Recommendation delete(Long id);
    List<Recommendation> getAllByPersonId(Long id);
    Recommendation generateImage(Recommendation entityToAddImage);
    List<Recommendation> getAllByPersonEntityIdAndFavorite(Long id);
    void deleteAllByPieceId(Long pieceId);
}
