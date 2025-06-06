package org.example.clothingrecommendationsystem.infrastructure.persistence.person;

import org.example.clothingrecommendationsystem.infrastructure.persistence.PhotoHandler;
import org.example.clothingrecommendationsystem.model.person.IPersonPhotoHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static org.example.clothingrecommendationsystem.infrastructure.persistence.PhotoHandler.addPhoto;

@Service
public class PersonPhotoHandler implements IPersonPhotoHandler {

    @Value("${external.person-path}")
    private String personPath;
    @Override
    public String addPiecePhoto(MultipartFile multipartFile) {
        return addPhoto(multipartFile, personPath);
    }



    @Override
    public String deletePiecePhoto(String pathToPhoto) {
        return PhotoHandler.deletePhoto(pathToPhoto);
    }
}
