package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;

import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageHandler;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedImageHandler implements IGeneratedImageHandler {
    @Override
    public GeneratedImage generateImage(String generationPrompt,
                                        List<PieceOfClothes> piecesOfClothesToGenerate,
                                        Person personToGenerate) {
        return null;
    }
}
