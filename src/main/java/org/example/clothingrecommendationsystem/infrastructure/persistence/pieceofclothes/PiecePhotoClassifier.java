package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.IPiecePhotoClassifier;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PiecePhotoClassifier implements IPiecePhotoClassifier {

    @Value("${external.pythonPath}")
    private String classificationApiUrl;

    @Override
    public Map<String, Object> classifyPiecePhoto(String pathToPhoto) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> result = new HashMap<>();

        String url = classificationApiUrl + "/photo_classification/classify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = Map.of("path", pathToPhoto);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to classify photo: " + response.getStatusCode());
        }

        result.put("name", response.getBody().get("name"));
        result.put("color", response.getBody().get("color"));
        result.put("material", response.getBody().get("material"));
        result.put("style", response.getBody().get("style"));
        result.put("temperatureCategories", Arrays.stream(
                response.getBody().get("temperatureCategories").toString().split("_"))
                .map(PieceOfClothes.TemperatureCategory::valueOf)
                .toList());
        result.put("characteristics", Arrays.stream(
                response.getBody().get("characteristics").toString().split("_"))
                .map(String::valueOf)
                .toList());
        return result;
    }
}
