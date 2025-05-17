package org.example.clothingrecommendationsystem.orchestrators.recommendation.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.List;

@Data
public class RecommendationDto {
    private Long id;
    private String userPrompt;
    private Person person;
    private List<GeneratedImage> generatedImages;
    private List<PieceOfClothes> recommendedClothes;
}
