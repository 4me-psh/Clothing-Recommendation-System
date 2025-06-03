package org.example.clothingrecommendationsystem.model.pieceofclothes;

import java.util.Map;

public interface IPiecePhotoClassifier {
    PieceOfClothes classifyPiecePhoto(String pathToPhoto);
}
