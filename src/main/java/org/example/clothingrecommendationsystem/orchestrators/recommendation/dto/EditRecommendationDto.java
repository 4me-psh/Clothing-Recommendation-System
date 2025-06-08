package org.example.clothingrecommendationsystem.orchestrators.recommendation.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto.PieceDto;

import java.util.List;

@Data
public class EditRecommendationDto {
    private String userPrompt;
    private List<GeneratedImage> generatedImages;
    private List<PieceDto> recommendedClothes;
    private Boolean favorite;
}
