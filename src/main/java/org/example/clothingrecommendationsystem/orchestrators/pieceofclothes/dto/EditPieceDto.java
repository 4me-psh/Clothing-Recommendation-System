package org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.List;

@Data
public class EditPieceDto {
    private String name;
    private String color;
    private String material;
    private PieceOfClothes.Style style;
    private String pieceCategory;
    private List<PieceOfClothes.TemperatureCategory> temperatureCategories;
    private List<String> characteristics;
}
