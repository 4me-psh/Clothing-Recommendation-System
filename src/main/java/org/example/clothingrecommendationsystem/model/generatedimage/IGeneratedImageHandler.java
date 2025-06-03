package org.example.clothingrecommendationsystem.model.generatedimage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IGeneratedImageHandler {
    String addGeneratedImage(String base64Image);
    String deleteGeneratedImage(String pathToPhoto);
    List<String> getAllGeneratedImagesByUser(List<String> pathsToPhotos);
    List<String> getAllGeneratedImages();
}
