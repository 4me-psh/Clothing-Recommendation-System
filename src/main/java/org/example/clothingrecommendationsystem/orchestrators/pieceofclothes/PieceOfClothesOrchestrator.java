package org.example.clothingrecommendationsystem.orchestrators.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.*;
import org.example.clothingrecommendationsystem.model.user.IUserOrchestrator;
import org.example.clothingrecommendationsystem.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class PieceOfClothesOrchestrator implements IPieceOfClothesOrchestrator {

    private final IPieceOfClothesRepository pieceOfClothesRepository;
    private final IPiecePhotoClassifier piecePhotoClassifier;
    private final IPiecePhotoHandler piecePhotoHandler;
    private final IUserOrchestrator userOrchestrator;

    @Autowired
    public PieceOfClothesOrchestrator(IPieceOfClothesRepository pieceOfClothesRepository,
                                      IPiecePhotoClassifier piecePhotoClassifier,
                                      IPiecePhotoHandler piecePhotoHandler,
                                      IUserOrchestrator userOrchestrator) {
        this.pieceOfClothesRepository = pieceOfClothesRepository;
        this.piecePhotoClassifier = piecePhotoClassifier;
        this.piecePhotoHandler = piecePhotoHandler;
        this.userOrchestrator = userOrchestrator;
    }

    @Override
    public List<PieceOfClothes> getAll() {
        return pieceOfClothesRepository.getAll();
    }

    @Override
    public PieceOfClothes getById(Long id) {
        return pieceOfClothesRepository.getById(id);
    }

    @Override
    public PieceOfClothes create(PieceOfClothes entityToCreate) {
        return pieceOfClothesRepository.create(entityToCreate);
    }

    @Override
    public PieceOfClothes edit(PieceOfClothes entityToUpdate) {
        return pieceOfClothesRepository.edit(entityToUpdate);
    }

    @Override
    public PieceOfClothes delete(Long id) {
        PieceOfClothes existingPiece = getById(id);
        pieceOfClothesRepository.delete(id);
        return existingPiece;
    }

    @Override
    public PieceOfClothes handlePieceOfClothes(Long userId, MultipartFile photo){
        String pathToSavedPhoto = piecePhotoHandler.addPiecePhoto(photo);
        Map<String, Object> classificationOfPhoto = piecePhotoClassifier.classifyPiecePhoto(pathToSavedPhoto);
        User user = userOrchestrator.getById(userId);
        PieceOfClothes pieceToSave = new PieceOfClothes();
        pieceToSave.setUser(user);
        pieceToSave.setName((String) classificationOfPhoto.get("name"));
        pieceToSave.setColor((String) classificationOfPhoto.get("color"));
        pieceToSave.setMaterial((String) classificationOfPhoto.get("material"));
        pieceToSave.setStyle(PieceOfClothes.Style.valueOf((String) classificationOfPhoto.get("style")));
        pieceToSave.setTemperatureCategories((List<PieceOfClothes.TemperatureCategory>) classificationOfPhoto.get("temperatureCategories"));
        pieceToSave.setCharacteristics((List<String>) classificationOfPhoto.get("characteristics"));
        pieceToSave.setPathToPhoto(pathToSavedPhoto);

        return create(pieceToSave);
    }

    @Override
    public List<PieceOfClothes> getAllByUserId(Long id) {
        return pieceOfClothesRepository.getAllByUserId(id);
    }
}
