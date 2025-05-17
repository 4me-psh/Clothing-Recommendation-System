package org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.model.user.User;

import java.util.List;

@Data
public class PieceDto {
    private Long id;
    private User user;
    private String name;
    private String color;
    private String material;
    private PieceOfClothes.Style style;
    private String pathToPhoto;
    private String pieceCategory;
    private List<PieceOfClothes.TemperatureCategory> temperatureCategories;
    private List<String> characteristics;
}
