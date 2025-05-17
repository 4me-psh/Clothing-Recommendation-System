package org.example.clothingrecommendationsystem.model.pieceofclothes;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPiecePhotoHandler {
    String addPiecePhoto(MultipartFile multipartFile);
    String deletePiecePhoto(String pathToPhoto);
    List<String> getAllPiecePhotosByUser(List<String> pathsToPhotos);
    List<String> getAllPiecePhotos();
}
