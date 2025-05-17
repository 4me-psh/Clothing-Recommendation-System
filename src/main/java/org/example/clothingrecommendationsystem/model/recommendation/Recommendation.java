package org.example.clothingrecommendationsystem.model.recommendation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Recommendation extends BaseModel {
    private Long id;
    private String userPrompt;
    private Person person;
    private List<GeneratedImage> generatedImages;
    private List<PieceOfClothes> recommendedClothes;
}
