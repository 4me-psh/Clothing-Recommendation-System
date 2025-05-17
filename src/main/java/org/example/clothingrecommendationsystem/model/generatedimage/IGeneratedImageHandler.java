package org.example.clothingrecommendationsystem.model.generatedimage;

import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.List;

public interface IGeneratedImageHandler {
    GeneratedImage generateImage(String generationPrompt,
                                 List<PieceOfClothes> piecesOfClothesToGenerate,
                                 Person personToGenerate);
}
