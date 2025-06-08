package org.example.clothingrecommendationsystem.orchestrators.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.*;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.user.IUserOrchestrator;
import org.example.clothingrecommendationsystem.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PieceOfClothesOrchestrator implements IPieceOfClothesOrchestrator {

    private final IPieceOfClothesRepository pieceOfClothesRepository;
    private final IPiecePhotoClassifier piecePhotoClassifier;
    private final IPiecePhotoHandler piecePhotoHandler;
    private final IUserOrchestrator userOrchestrator;
    private final IPieceRemoveBgHandler pieceRemoveBgHandler;
    private final IRecommendationOrchestrator recommendationOrchestrator;

    @Autowired
    public PieceOfClothesOrchestrator(IPieceOfClothesRepository pieceOfClothesRepository,
                                      IPiecePhotoClassifier piecePhotoClassifier,
                                      IPiecePhotoHandler piecePhotoHandler,
                                      IUserOrchestrator userOrchestrator, IPieceRemoveBgHandler pieceRemoveBgHandler, IRecommendationOrchestrator recommendationOrchestrator) {
        this.pieceOfClothesRepository = pieceOfClothesRepository;
        this.piecePhotoClassifier = piecePhotoClassifier;
        this.piecePhotoHandler = piecePhotoHandler;
        this.userOrchestrator = userOrchestrator;
        this.pieceRemoveBgHandler = pieceRemoveBgHandler;
        this.recommendationOrchestrator = recommendationOrchestrator;
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
        piecePhotoHandler.deletePiecePhoto(existingPiece.getPathToPhoto());
        piecePhotoHandler.deletePiecePhoto(existingPiece.getPathToRemovedBgPhoto());
        recommendationOrchestrator.deleteAllByPieceId(id);
        pieceOfClothesRepository.delete(id);
        return existingPiece;
    }

    @Override
    public PieceOfClothes handlePieceOfClothes(Long userId, MultipartFile photo){
        String pathToSavedPhoto = piecePhotoHandler.addPiecePhoto(photo);
        String pathToRemovedBgPhoto = pieceRemoveBgHandler.removeBg(pathToSavedPhoto);
        PieceOfClothes pieceToSave = piecePhotoClassifier.classifyPiecePhoto(pathToRemovedBgPhoto);

        User user = userOrchestrator.getById(userId);

        pieceToSave.setUser(user);
        pieceToSave.setPathToPhoto(pathToSavedPhoto);
        pieceToSave.setPathToRemovedBgPhoto(pathToRemovedBgPhoto);

        return create(pieceToSave);
    }

    @Override
    public List<PieceOfClothes> getAllByUserId(Long id) {
        return pieceOfClothesRepository.getAllByUserId(id);
    }
}
