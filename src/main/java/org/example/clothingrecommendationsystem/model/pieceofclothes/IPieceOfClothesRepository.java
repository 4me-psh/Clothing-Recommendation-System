package org.example.clothingrecommendationsystem.model.pieceofclothes;

import java.util.List;

public interface IPieceOfClothesRepository {
    List<PieceOfClothes> getAll();
    PieceOfClothes getById(Long id);
    PieceOfClothes create(PieceOfClothes entityToCreate);
    PieceOfClothes edit(PieceOfClothes entityToUpdate);
    void delete(Long id);
    List<PieceOfClothes> getAllByUserId(Long id);
}
