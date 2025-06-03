package org.example.clothingrecommendationsystem.orchestrators.generatedimage;

import org.example.clothingrecommendationsystem.model.generatedimage.*;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedImageOrchestrator implements IGeneratedImageOrchestrator {
    private final IGeneratedImageRepository generatedImageRepository;
    private final IGeneratedImageHandler generatedImageHandler;
    private final IGeneratedImageGenerator generatedImageGenerator;

    public GeneratedImageOrchestrator(IGeneratedImageRepository generatedImageRepository, IGeneratedImageHandler generatedImageHandler, IGeneratedImageGenerator generatedImageGenerator) {
        this.generatedImageRepository = generatedImageRepository;
        this.generatedImageHandler = generatedImageHandler;
        this.generatedImageGenerator = generatedImageGenerator;
    }

    @Override
    public List<GeneratedImage> getAll() {
        return generatedImageRepository.getAll();
    }

    @Override
    public GeneratedImage getById(Long id) {
        return generatedImageRepository.getById(id);
    }

    @Override
    public GeneratedImage create(GeneratedImage entityToCreate) {
        return generatedImageRepository.create(entityToCreate);
    }

    @Override
    public GeneratedImage edit(GeneratedImage entityToUpdate) {
        return generatedImageRepository.edit(entityToUpdate);
    }

    @Override
    public GeneratedImage delete(Long id) {
        GeneratedImage existingImage = getById(id);
        generatedImageRepository.delete(id);
        return existingImage;
    }

    @Override
    public GeneratedImage generateImage(List<PieceOfClothes> piecesOfClothesToGenerate, Person personToGenerate) {
        String base64Image = generatedImageGenerator.generateImage(piecesOfClothesToGenerate, personToGenerate);
        String pathToImage = generatedImageHandler.addGeneratedImage(base64Image);

        GeneratedImage generatedImage = new GeneratedImage();
        generatedImage.setPerson(personToGenerate);
        generatedImage.setPathToImage(pathToImage);

        return create(generatedImage);
    }
}
