package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto.PieceDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.CreateRecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.EditRecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.RecommendationDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Operation(summary = "Get all Recommendations")
    @GetMapping
    public ResponseEntity<List<RecommendationDto>> getAllRecommendations() {
        List<Recommendation> entities = recommendationOrchestrator.getAll();
        List<RecommendationDto> recommendationDtos = entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recommendationDtos);
    }

    @Operation(summary = "Generate a new Recommendation")
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<RecommendationDto> generateRecommendation(@RequestBody CreateRecommendationDto recommendationDto) {
        Recommendation recommendationToCreate = modelMapper.map(recommendationDto, Recommendation.class);
        Recommendation createdRecommendation = recommendationOrchestrator.create(recommendationToCreate);
        RecommendationDto createdRecommendationDto = toDto(createdRecommendation);
        return ResponseEntity.ok(createdRecommendationDto);
    }

    @Operation(summary = "Get Recommendation by Id")
    @GetMapping(path="/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<RecommendationDto> getRecommendationById(@PathVariable Long id) {
        Recommendation entity = recommendationOrchestrator.getById(id);
        RecommendationDto dto = toDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Update Recommendation")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecommendation(@PathVariable Long id, @RequestBody EditRecommendationDto recommendationDto) {
        Recommendation editEntity = recommendationOrchestrator.getById(id);
        modelMapper.map(recommendationDto, editEntity);
        recommendationOrchestrator.edit(editEntity);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Delete Recommendation")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecommendation(@PathVariable Long id) {
        Recommendation entityToDelete = recommendationOrchestrator.getById(id);
        recommendationOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }

    @Operation(summary = "Get all Recommendations By Person Id")
    @GetMapping(path="/person/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<RecommendationDto>> getAllRecommendationsByPersonId(@PathVariable Long id) {
        List<Recommendation> entities = recommendationOrchestrator.getAllByPersonId(id);
        List<RecommendationDto> recommendationDtos = entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recommendationDtos);
    }

    @Operation(summary = "Generate Image in Recommendation")
    @PutMapping(path="/generate/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> generateImageInRecommendation(@PathVariable Long id) {
        Recommendation editEntity = recommendationOrchestrator.getById(id);
        recommendationOrchestrator.generateImage(editEntity);
        return ResponseEntity.ok("ok image added");
    }

    @Operation(summary = "Get all Favorite Recommendations By Person Id")
    @GetMapping(path="/person/favorites/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<RecommendationDto>> getAllFavoriteRecommendationsByPersonId(@PathVariable Long id) {
        List<Recommendation> entities = recommendationOrchestrator.getAllByPersonEntityIdAndFavorite(id);
        List<RecommendationDto> recommendationDtos = entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(recommendationDtos);
    }

    private RecommendationDto toDto(Recommendation entity) {
        RecommendationDto dto = modelMapper.map(entity, RecommendationDto.class);

        List<PieceDto> mappedPieces = Optional.ofNullable(entity.getRecommendedClothes())
                .orElse(List.of())
                .stream()
                .map(piece -> modelMapper.map(piece, PieceDto.class))
                .collect(Collectors.toList());

        List<String> imagePaths = Optional.ofNullable(entity.getGeneratedImages())
                .orElse(List.of())
                .stream()
                .map(GeneratedImage::getPathToImage)
                .collect(Collectors.toList());

        dto.setRecommendedClothes(mappedPieces);
        dto.setGeneratedImages(imagePaths);
        return dto;
    }
}
