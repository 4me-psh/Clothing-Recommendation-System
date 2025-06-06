package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.CreateRecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.EditRecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.RecommendationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations")
@Tag(name = "Recommendation", description = "Clothing Recommendation")
@CrossOrigin(origins={"http://localhost:8080"})
public class RecommendationController {

    private final IRecommendationOrchestrator recommendationOrchestrator;
    private final ModelMapper modelMapper;
    @Autowired
    public RecommendationController(IRecommendationOrchestrator recommendationOrchestrator,
                                    @Qualifier("orchestratorModelMapper") ModelMapper modelMapper) {
        this.recommendationOrchestrator = recommendationOrchestrator;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all Recommendations", description = "Gets all Recommendations")
    @GetMapping
    public ResponseEntity<List<RecommendationDto>> getAllRecommendations() {
        List<Recommendation> entities = recommendationOrchestrator.getAll();
        List<RecommendationDto> recommendationDtos = new ArrayList<>(List.of());
        for (Recommendation entity : entities) {
            recommendationDtos.add(modelMapper.map(entity, RecommendationDto.class));
        }
        return ResponseEntity.ok(recommendationDtos);
    }

    @Operation(summary = "Generate a new Recommendation", description = "Generates a new Recommendation")
    @PostMapping
    public ResponseEntity<RecommendationDto> generateRecommendation(@RequestBody CreateRecommendationDto recommendationDto) {
        Recommendation recommendationToCreate = modelMapper.map(recommendationDto, Recommendation.class);
        Recommendation createdRecommendation = recommendationOrchestrator.create(recommendationToCreate);
        RecommendationDto createdRecommendationDto = modelMapper.map(createdRecommendation, RecommendationDto.class);
        return ResponseEntity.ok(createdRecommendationDto);
    }

    @Operation(summary = "Get Recommendation by Id", description = "Gets the Recommendation by their Id")
    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDto> getPersonById(@PathVariable Long id) {
        Recommendation entity = recommendationOrchestrator.getById(id);
        RecommendationDto recommendationDto = modelMapper.map(entity, RecommendationDto.class);
        return ResponseEntity.ok(recommendationDto);
    }

    @Operation(summary = "Update Recommendation", description = "Updates the Recommendation by its Id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecommendation(@PathVariable Long id, @RequestBody EditRecommendationDto recommendationDto) {
        Recommendation editEntity = recommendationOrchestrator.getById(id);
        modelMapper.map(recommendationDto, editEntity);
        recommendationOrchestrator.edit(editEntity);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Delete Recommendation", description = "Deletes the Recommendation by their Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecommendation(@PathVariable Long id) {
        Recommendation entityToDelete = recommendationOrchestrator.getById(id);
        recommendationOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }

    @Operation(summary = "Get all Recommendations By Person Id", description = "Gets all Recommendations By Person Id")
    @GetMapping("/person/{id}")
    public ResponseEntity<List<RecommendationDto>> getAllRecommendationsByPersonId(@PathVariable Long id) {
        List<Recommendation> entities = recommendationOrchestrator.getAllByPersonId(id);
        List<RecommendationDto> recommendationDtos = new ArrayList<>(List.of());
        for (Recommendation entity : entities) {
            recommendationDtos.add(modelMapper.map(entity, RecommendationDto.class));
        }
        return ResponseEntity.ok(recommendationDtos);
    }

    @Operation(summary = "Generate Image in Recommendation", description = "Generates Image in the Recommendation by its Id")
    @PutMapping("/generate/{id}")
    public ResponseEntity<String> generateImageInRecommendation(@PathVariable Long id) {
        Recommendation editEntity = recommendationOrchestrator.getById(id);
        recommendationOrchestrator.generateImage(editEntity);
        return ResponseEntity.ok("ok image added");
    }

    @Operation(summary = "Get all Favorite Recommendations By Person Id", description = "Gets all Favorite Recommendations By Person Id")
    @GetMapping("/person/favorites/{id}")
    public ResponseEntity<List<RecommendationDto>> getAllFavoriteRecommendationsByPersonId(@PathVariable Long id) {
        List<Recommendation> entities = recommendationOrchestrator.getAllByPersonEntityIdAndFavorite(id);
        List<RecommendationDto> recommendationDtos = new ArrayList<>(List.of());
        for (Recommendation entity : entities) {
            recommendationDtos.add(modelMapper.map(entity, RecommendationDto.class));
        }
        return ResponseEntity.ok(recommendationDtos);
    }
}