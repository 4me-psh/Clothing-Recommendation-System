package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;

import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageGenerator;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedImageGenerator implements IGeneratedImageGenerator {

    @Override
    public String generateImage(List<PieceOfClothes> piecesOfClothesToGenerate, Person personToGenerate) {
        return null;
    }
}
