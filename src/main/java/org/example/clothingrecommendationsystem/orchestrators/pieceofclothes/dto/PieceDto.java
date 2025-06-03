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
    private List<PieceOfClothes.Style> styles;
    private String pathToPhoto;
    private String pathToRemovedBgPhoto;
    private PieceOfClothes.PieceCategory pieceCategory;
    private List<PieceOfClothes.TemperatureCategory> temperatureCategories;
    private List<String> characteristics;
    private Boolean useRemovedBg;
}
