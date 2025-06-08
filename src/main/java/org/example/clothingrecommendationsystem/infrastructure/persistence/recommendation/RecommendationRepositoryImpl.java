package org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation;
import org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage.GeneratedImageEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.RecommendationRepository;
import org.example.clothingrecommendationsystem.infrastructure.persistence.person.PersonEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes.PieceOfClothesEntity;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationRepository;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Recommendation recommendation = modelMapper.map(recommendationEntity, Recommendation.class);
        assert recommendationEntity != null;
        List<GeneratedImage> mappedImages = recommendationEntity.getGeneratedImageEntities().stream()
                .map(piece -> modelMapper.map(piece, GeneratedImage.class))
                .toList();
        recommendation.setGeneratedImages(mappedImages);
        return recommendation;
    }

    @Override
    public Recommendation create(Recommendation entityToCreate) {

        List<PieceOfClothesEntity> mappedPieces = entityToCreate.getRecommendedClothes().stream()
                .map(piece -> modelMapper.map(piece, PieceOfClothesEntity.class))
                .toList();

        RecommendationEntity recommendationEntity = modelMapper.map(entityToCreate, RecommendationEntity.class);

        recommendationEntity.setPersonEntity(modelMapper.map(entityToCreate.getPerson(), PersonEntity.class));
        recommendationEntity.setRecommendedClothesEntities(mappedPieces);


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

    @Override
    public List<Recommendation> getAllByPersonId(Long id) {
        List<Recommendation> recommendations = new ArrayList<>();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findAllByPersonEntityId(id);
        for (RecommendationEntity recommendationEntity : recommendationEntities) {
            recommendations.add(modelMapper.map(recommendationEntity, Recommendation.class));
        }
        return recommendations;
    }

    @Override
    public Recommendation generateImage(Recommendation entityToAddImage) {
        RecommendationEntity editedEntity = recommendationRepository.findById(entityToAddImage.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToAddImage, editedEntity);
        List<GeneratedImageEntity> mappedImages = entityToAddImage.getGeneratedImages().stream()
                .map(piece -> modelMapper.map(piece, GeneratedImageEntity.class))
                .collect(Collectors.toCollection(ArrayList::new));

        editedEntity.setGeneratedImageEntities(mappedImages);
        recommendationRepository.save(editedEntity);

        return modelMapper.map(editedEntity, Recommendation.class);
    }

    @Override
    public List<Recommendation> getAllByPersonEntityIdAndFavorite(Long id) {
        List<Recommendation> recommendations = new ArrayList<>();
        List<RecommendationEntity> recommendationEntities = recommendationRepository.findAllByPersonEntityIdAndFavorite(id, true);
        for (RecommendationEntity recommendationEntity : recommendationEntities) {
            recommendations.add(modelMapper.map(recommendationEntity, Recommendation.class));
        }
        return recommendations;
    }


}

