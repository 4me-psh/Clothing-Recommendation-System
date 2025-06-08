package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;

import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class GeneratedImageHandler implements IGeneratedImageHandler {

    @Value("${external.generated-images-path}")
    private String uploadDir;

    @Override
    public String addGeneratedImage(String base64Image) {
        String fileName = UUID.randomUUID() + ".png";
        Path path = Paths.get(uploadDir, fileName);
        try {
            byte[] decodedImage = Base64.getDecoder().decode(base64Image);

            Files.createDirectories(path.getParent());
            Files.write(path, decodedImage);

        } catch (IOException e) {
            throw new RuntimeException("Не вдалося зберегти зображення", e);
        }
        return path.toString();
    }

    @Override
    public String deleteGeneratedImage(String pathToPhoto) {

        Path path = Paths.get(pathToPhoto);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося видалити зображення", e);
        }
        return path.toString();
    }

    @Override
    public List<String> getAllGeneratedImagesByUser(List<String> pathsToPhotos) {
        return List.of();
    }

    @Override
    public List<String> getAllGeneratedImages() {
        return List.of();
    }
}
