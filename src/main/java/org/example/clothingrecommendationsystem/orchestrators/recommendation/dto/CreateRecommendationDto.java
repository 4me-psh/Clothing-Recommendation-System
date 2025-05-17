package org.example.clothingrecommendationsystem.orchestrators.recommendation.dto;

import lombok.Data;

@Data
public class CreateRecommendationDto {
    private String userPrompt;
    private Long personId;
}
