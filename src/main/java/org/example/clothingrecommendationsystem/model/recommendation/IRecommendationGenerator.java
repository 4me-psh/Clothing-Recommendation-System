package org.example.clothingrecommendationsystem.model.recommendation;

public interface IRecommendationGenerator {
    Recommendation generateRecommendation(String generationCharacteristics, Long userId);
}
