package org.example.clothingrecommendationsystem.orchestrators.recommendation;

import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationGenerator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationRepository;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationOrchestrator implements IRecommendationOrchestrator {
    private final IRecommendationRepository recommendationRepository;
    private final IGeneratedImageOrchestrator generatedImageOrchestrator;
    private final IRecommendationGenerator recommendationGenerator;

    @Autowired
    public RecommendationOrchestrator(IRecommendationRepository recommendationRepository,
                                      IGeneratedImageOrchestrator generatedImageOrchestrator,
                                      IRecommendationGenerator recommendationGenerator) {

        this.recommendationRepository = recommendationRepository;
        this.generatedImageOrchestrator = generatedImageOrchestrator;
        this.recommendationGenerator = recommendationGenerator;
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
        entityToCreate.setRecommendedClothes(recommendationGenerator.generateRecommendation(entityToCreate.getUserPrompt(), entityToCreate.getPerson().getUser().getId()).getRecommendedClothes());
        entityToCreate.setGeneratedImages(List.of(generatedImageOrchestrator.generateImage(entityToCreate.getRecommendedClothes(), entityToCreate.getPerson())));
        return recommendationRepository.create(entityToCreate);
    }

    @Override
    public Recommendation edit(Recommendation entityToUpdate) {
        return recommendationRepository.edit(entityToUpdate);
    }

    @Override
    public Recommendation delete(Long id) {
        Recommendation existingRecommendation = getById(id);
        recommendationRepository.delete(id);
        return existingRecommendation;
    }
}
