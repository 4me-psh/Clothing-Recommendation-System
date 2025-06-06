package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.IPiecePhotoHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.example.clothingrecommendationsystem.infrastructure.persistence.PhotoHandler.addPhoto;
import static org.example.clothingrecommendationsystem.infrastructure.persistence.PhotoHandler.deletePhoto;

@Service
public class PiecePhotoHandler implements IPiecePhotoHandler {

    @Value("${external.clothing-photos-path}")
    private String uploadDir;

    @Override
    public String addPiecePhoto(MultipartFile multipartFile) {
        return addPhoto(multipartFile, uploadDir);
    }

    @Override
    public String deletePiecePhoto(String pathToPhoto) {
        return deletePhoto(pathToPhoto);
    }



    @Override
    public List<String> getAllPiecePhotosByUser(List<String> pathsToPhotos) {
        return List.of();
    }

    @Override
    public List<String> getAllPiecePhotos() {
        return List.of();
    }
}
