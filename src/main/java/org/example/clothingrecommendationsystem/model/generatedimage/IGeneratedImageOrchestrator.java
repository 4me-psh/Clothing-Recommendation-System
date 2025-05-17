package org.example.clothingrecommendationsystem.model.generatedimage;

import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.List;

public interface IGeneratedImageOrchestrator {
    List<GeneratedImage> getAll();
    GeneratedImage getById(Long id);
    GeneratedImage create(GeneratedImage entityToCreate);
    GeneratedImage edit(GeneratedImage entityToUpdate);
    GeneratedImage delete(Long id);
    GeneratedImage generateImage(List<PieceOfClothes> piecesOfClothesToGenerate,
                                 Person personToGenerate);
}
