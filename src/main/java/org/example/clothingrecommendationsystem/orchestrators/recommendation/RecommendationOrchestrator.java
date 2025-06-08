package org.example.clothingrecommendationsystem.orchestrators.recommendation;

import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageOrchestrator;
import org.example.clothingrecommendationsystem.model.person.IPersonOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationGenerator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationRepository;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationOrchestrator implements IRecommendationOrchestrator {
    private final IRecommendationRepository recommendationRepository;
    private final IGeneratedImageOrchestrator generatedImageOrchestrator;
    private final IRecommendationGenerator recommendationGenerator;
    private final IPersonOrchestrator personOrchestrator;

    @Autowired
    public RecommendationOrchestrator(IRecommendationRepository recommendationRepository,
                                      IGeneratedImageOrchestrator generatedImageOrchestrator,
                                      IRecommendationGenerator recommendationGenerator, IPersonOrchestrator personOrchestrator) {

        this.recommendationRepository = recommendationRepository;
        this.generatedImageOrchestrator = generatedImageOrchestrator;
        this.recommendationGenerator = recommendationGenerator;
        this.personOrchestrator = personOrchestrator;
    }

    @Override
    public List<Recommendation> getAll() {
        return recommendationRepository.getAll();
    }

    @Override
    public Recommendation getById(Long id) {
        return recommendationRepository.getById(id);
    }

    @Override
    public Recommendation create(Recommendation entityToCreate) {
        entityToCreate.setPerson(personOrchestrator.getById(entityToCreate.getPerson().getId()));
        entityToCreate.setRecommendedClothes(recommendationGenerator.generateRecommendation(entityToCreate.getUserPrompt(), entityToCreate.getPerson().getUser().getId()).getRecommendedClothes());
        return recommendationRepository.create(entityToCreate);
    }

    @Override
    public Recommendation edit(Recommendation entityToUpdate) {
        return recommendationRepository.edit(entityToUpdate);
    }

    @Override
    public Recommendation delete(Long id) {
        Recommendation existingRecommendation = getById(id);

        List<GeneratedImage> imagesToDelete = Optional.ofNullable(existingRecommendation.getGeneratedImages())
                .orElse(List.of());

        for (GeneratedImage image : imagesToDelete) {
            if (image.getId() != null) {
                generatedImageOrchestrator.deleteFromDisc(image.getPathToImage());
            }
        }

        recommendationRepository.delete(id);

        return existingRecommendation;
    }


    @Override
    public List<Recommendation> getAllByPersonId(Long id) {
        return recommendationRepository.getAllByPersonId(id);
    }

    @Override
    public Recommendation generateImage(Recommendation entityToGenerate) {
        ArrayList<GeneratedImage> generatedImages = new ArrayList<>(
                Optional.ofNullable(entityToGenerate.getGeneratedImages()).orElse(List.of())
        );
        generatedImages.add(generatedImageOrchestrator.generateImage(entityToGenerate.getRecommendedClothes(), entityToGenerate.getPerson()));
        entityToGenerate.setGeneratedImages(generatedImages);
        return recommendationRepository.generateImage(entityToGenerate);
    }

    @Override
    public List<Recommendation> getAllByPersonEntityIdAndFavorite(Long id) {
        return recommendationRepository.getAllByPersonEntityIdAndFavorite(id);
    }

    @Override
    public void deleteAllByPieceId(Long pieceId) {
        recommendationRepository.deleteAllByPieceId(pieceId);
    }

}
