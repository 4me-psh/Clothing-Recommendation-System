package org.example.clothingrecommendationsystem.model.person;

import org.springframework.web.multipart.MultipartFile;

public interface IPersonPhotoHandler {
    String addPiecePhoto(MultipartFile multipartFile);
    String deletePiecePhoto(String pathToPhoto);
}
