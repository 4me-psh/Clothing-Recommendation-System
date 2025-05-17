package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.IPiecePhotoHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

import java.util.List;
import java.util.UUID;

@Service
public class PiecePhotoHandler implements IPiecePhotoHandler {

    @Value("${external.clothing-photos-path}")
    private String uploadDir;

    @Override
    public String addPiecePhoto(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося зберегти фото", e);
        }
        return path.toString();
    }

    @Override
    public String deletePiecePhoto(String pathToPhoto) {
        Path path = Paths.get(uploadDir, pathToPhoto);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося видалити фото", e);
        }
        return path.toString();
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
