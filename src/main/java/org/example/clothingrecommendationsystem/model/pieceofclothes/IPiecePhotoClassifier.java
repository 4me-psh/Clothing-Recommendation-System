package org.example.clothingrecommendationsystem.model.pieceofclothes;

import java.util.Map;

public interface IPiecePhotoClassifier {
    Map<String, Object> classifyPiecePhoto(String pathToPhoto);
}
