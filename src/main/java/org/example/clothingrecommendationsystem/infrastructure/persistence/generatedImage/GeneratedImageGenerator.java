package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageGenerator;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeneratedImageGenerator implements IGeneratedImageGenerator {
    @Value("${external.pythonPath}")
    private String base_url;

    private final RestTemplate rest = new RestTemplate();

    @Override
    public String generateImage(List<PieceOfClothes> piecesOfClothes, Person person) {

        List<String> clothesPaths = piecesOfClothes.stream()
                .map(PieceOfClothes::getPathToPhoto)
                .filter(p -> p != null && !p.isBlank())
                .collect(Collectors.toList());

        Object personBlock;
        if (person.getPathToPerson() != null && !person.getPathToPerson().isBlank()) {
            personBlock = person.getPathToPerson();
        } else {
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("gender", person.getGender().name().toLowerCase());
            attrs.put("skinTone", person.getSkinTone().name().toLowerCase());
            attrs.put("hairColor", person.getHairColor());
            attrs.put("height", person.getHeight());
            attrs.put("age", person.getAge());
            personBlock = attrs;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("image_paths", clothesPaths);
        body.put("person", personBlock);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = base_url + "/image_generation/generate";

        ResponseEntity<String> resp = rest.postForEntity(url, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json;
        try {
            json = mapper.readTree(resp.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json.get("base64_image").asText();
    }

    public String generateImageStable(List<PieceOfClothes> clothes, Person person) {

        List<Map<String, Object>> clothesAttributes = clothes.stream().map(piece -> {
            Map<String, Object> item = new HashMap<>();
            item.put("name", piece.getName());
            item.put("color", piece.getColor());
            item.put("material", piece.getMaterial());
            item.put("characteristics", piece.getCharacteristics());
            return item;
        }).collect(Collectors.toList());

        Map<String, Object> personAttributes = new HashMap<>();
        personAttributes.put("gender", person.getGender().name().toLowerCase());
        personAttributes.put("skinTone", person.getSkinTone().name().toLowerCase());
        personAttributes.put("hairColor", person.getHairColor());
        personAttributes.put("height", person.getHeight());
        personAttributes.put("age", person.getAge());

        Map<String, Object> body = new HashMap<>();
        body.put("person", personAttributes);
        body.put("clothes", clothesAttributes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String url = base_url + "/image_generation/stable";

        ResponseEntity<String> resp = rest.postForEntity(url, entity, String.class);

        return resp.getBody();
    }
}
