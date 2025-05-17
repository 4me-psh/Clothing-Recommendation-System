package org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation;
import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.RecommendationRepository;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationRepository;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecommendationRepositoryImpl implements IRecommendationRepository {
    private final RecommendationRepository recommendationRepository;
    private final ModelMapper modelMapper;

    @Lazy
    public RecommendationRepositoryImpl(RecommendationRepository recommendationRepository,
                                        @Qualifier("entityModelMapper") ModelMapper modelMapper) {
        this.recommendationRepository = recommendationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Recommendation> getAll() {
        List<Recommendation> recommendations = new ArrayList<>();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findAll();
        for (RecommendationEntity recommendationEntity : recommendationEntities) {
            recommendations.add(modelMapper.map(recommendationEntity, Recommendation.class));
        }
        return recommendations;
    }

    @Override
    public Recommendation getById(Long id) {
        RecommendationEntity recommendationEntity = recommendationRepository.findById(id).orElse(null);
        return modelMapper.map(recommendationEntity, Recommendation.class);
    }

    @Override
    public Recommendation create(Recommendation entityToCreate) {
        RecommendationEntity recommendationEntity = modelMapper.map(entityToCreate, RecommendationEntity.class);
        RecommendationEntity createdRecommendationEntity = recommendationRepository.save(recommendationEntity);
        return modelMapper.map(createdRecommendationEntity, Recommendation.class);
    }

    @Override
    public Recommendation edit(Recommendation entityToUpdate) {
        RecommendationEntity editedEntity = recommendationRepository.findById(entityToUpdate.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToUpdate, editedEntity);
        recommendationRepository.save(editedEntity);
        return modelMapper.map(editedEntity, Recommendation.class);
    }

    @Override
    public void delete(Long id) {
        RecommendationEntity entity = recommendationRepository.findById(id).orElse(null);
        assert entity != null;
        recommendationRepository.delete(entity);
    }

}

