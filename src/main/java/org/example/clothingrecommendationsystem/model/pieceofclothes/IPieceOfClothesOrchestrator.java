package org.example.clothingrecommendationsystem.model.pieceofclothes;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPieceOfClothesOrchestrator {
    List<PieceOfClothes> getAll();
    PieceOfClothes getById(Long id);
    PieceOfClothes create(PieceOfClothes entityToCreate);
    PieceOfClothes edit(PieceOfClothes entityToUpdate);
    PieceOfClothes delete(Long id);
    PieceOfClothes handlePieceOfClothes(Long userId, MultipartFile photo);
    List<PieceOfClothes> getAllByUserId(Long id);
}
