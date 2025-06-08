package org.example.clothingrecommendationsystem.model.generatedimage;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.io.IOException;
import java.util.List;

public interface IGeneratedImageGenerator {
    String generateImage(List<PieceOfClothes> piecesOfClothesToGenerate,
                                 Person personToGenerate);
}
