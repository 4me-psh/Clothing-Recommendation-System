package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceRemoveBgHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Service
public class PieceRemoveBgHandler implements IPieceRemoveBgHandler {

    @Value("${external.pythonPath}")
    private String pythonServiceUrl;

    @Value("${external.removed-b-gPhotos-path}")
    private String outputDir;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public String removeBg(String pathToPhoto) {

        String url = pythonServiceUrl + "/remove_background/remove";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = Map.of("path", pathToPhoto);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity(url, request, Map.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to remove background: "
                    + e.getStatusCode() + " / " + e.getResponseBodyAsString());
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to remove background: HTTP " + response.getStatusCode());
        }

        String dataUri = (String) response.getBody().get("image");
        if (dataUri == null || !dataUri.startsWith("data:image/png;base64,")) {
            throw new RuntimeException("Unexpected response payload: " + dataUri);
        }

        String b64 = dataUri.substring(dataUri.indexOf(',') + 1);
        byte[] imageBytes = Base64.getDecoder().decode(b64);

        Path dir = Paths.get(outputDir);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory: " + dir, e);
        }

        String originalName = Paths.get(pathToPhoto).getFileName().toString();
        String baseName = originalName.contains(".")
                ? originalName.substring(0, originalName.lastIndexOf('.'))
                : originalName;
        String fileName = baseName + "_" + UUID.randomUUID() + ".png";

        Path outPath = dir.resolve(fileName);

        try {
            Files.write(outPath, imageBytes, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save removed-bg image to disk: " + outPath, e);
        }

        return outPath.toAbsolutePath().toString();
    }
}
