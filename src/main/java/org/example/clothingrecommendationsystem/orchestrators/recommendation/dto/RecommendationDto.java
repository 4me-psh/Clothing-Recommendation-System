package org.example.clothingrecommendationsystem.orchestrators.recommendation.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto.PieceDto;

import java.util.Date;
import java.util.List;

@Data
public class RecommendationDto {
    private Long id;
    private String userPrompt;
    private Long personId;
    private List<String> generatedImages;
    private List<PieceDto> recommendedClothes;
    private Boolean favorite;
    protected Date createdAt;
    protected Date updatedAt;

}
