package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.IPiecePhotoClassifier;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PiecePhotoClassifier implements IPiecePhotoClassifier {

    @Value("${external.pythonPath}")
    private String classificationApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PieceOfClothes classifyPiecePhoto(String pathToPhoto) {
        String url = classificationApiUrl + "/photo_classification/classify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = Map.of("path", pathToPhoto);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity(url, request, Map.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(
                    "Failed to classify photo: " + e.getStatusCode() + " / " + e.getResponseBodyAsString()
            );
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to classify photo: " + response.getStatusCode());
        }

        Map<String, Object> body = response.getBody();

        String name     = (String) body.get("name");
        String color    = (String) body.get("color");
        String material = (String) body.get("material");

        @SuppressWarnings("unchecked")
        List<String> stylesRaw = (List<String>) body.get("styles");
        List<PieceOfClothes.Style> styles =
                stylesRaw.stream()
                        .map(String::trim)
                        .map(PieceOfClothes.Style::valueOf)
                        .toList();
        PieceOfClothes.PieceCategory pieceCategory =
                PieceOfClothes.PieceCategory.valueOf(((String) body.get("pieceCategory")).trim());

        @SuppressWarnings("unchecked")
        List<String> tempCatsRaw = (List<String>) body.get("temperatureCategories");
        List<PieceOfClothes.TemperatureCategory> temperatureCategories =
                tempCatsRaw.stream()
                        .map(String::trim)
                        .map(PieceOfClothes.TemperatureCategory::valueOf)
                        .collect(Collectors.toList());

        @SuppressWarnings("unchecked")
        List<String> characteristics =
                (List<String>) body.get("characteristics");

        PieceOfClothes piece = new PieceOfClothes();
        piece.setName(name);
        piece.setColor(color);
        piece.setMaterial(material);
        piece.setStyles(styles);
        piece.setPieceCategory(pieceCategory);
        piece.setPathToPhoto(pathToPhoto);
        piece.setTemperatureCategories(temperatureCategories);
        piece.setCharacteristics(characteristics);

        return piece;
    }
}
