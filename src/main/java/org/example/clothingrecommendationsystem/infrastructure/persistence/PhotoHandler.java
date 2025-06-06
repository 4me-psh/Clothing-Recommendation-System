package org.example.clothingrecommendationsystem.infrastructure.persistence;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoHandler {

    public static String addPhoto(MultipartFile multipartFile, String personPath) {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        Path path = Paths.get(personPath, fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося зберегти фото", e);
        }
        return path.toString();
    }

    public static String deletePhoto(String pathToPhoto) {
        Path path = Paths.get(pathToPhoto);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося видалити фото", e);
        }
        return path.toString();
    }
}
