package org.example.clothingrecommendationsystem.orchestrators.generatedimage;

import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageOrchestrator;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedImageOrchestrator implements IGeneratedImageOrchestrator {
    @Override
    public List<GeneratedImage> getAll() {
        return List.of();
    }

    @Override
    public GeneratedImage getById(Long id) {
        return null;
    }

    @Override
    public GeneratedImage create(GeneratedImage entityToCreate) {
        return null;
    }

    @Override
    public GeneratedImage edit(GeneratedImage entityToUpdate) {
        return null;
    }

    @Override
    public GeneratedImage delete(Long id) {
        return null;
    }

    @Override
    public GeneratedImage generateImage(List<PieceOfClothes> piecesOfClothesToGenerate, Person personToGenerate) {
        return null;
    }
}
