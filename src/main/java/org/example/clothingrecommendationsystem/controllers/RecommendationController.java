package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.recommendation.IRecommendationOrchestrator;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.CreateRecommendationDto;
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

//    @Operation(summary = "Get all Persons", description = "Gets all Persons")
//    @GetMapping
//    public ResponseEntity<List<PersonDto>> getAllPersons() {
//        List<Person> entities = personOrchestrator.getAll();
//        List<PersonDto> personDtos = new ArrayList<>(List.of());
//        for (Person entity : entities) {
//            personDtos.add(modelMapper.map(entity, PersonDto.class));
//        }
//        return ResponseEntity.ok(personDtos);
//    }

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

//    @Operation(summary = "Update Person", description = "Updates the Person by their Id")
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody EditPersonDto personDto) {
//        Person editEntity = personOrchestrator.getById(id);
//        modelMapper.map(personDto, editEntity);
//        personOrchestrator.edit(editEntity);
//        return ResponseEntity.ok("ok edit");
//    }

    @Operation(summary = "Delete Recommendation", description = "Deletes the Recommendation by their Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecommendation(@PathVariable Long id) {
        Recommendation entityToDelete = recommendationOrchestrator.getById(id);
        recommendationOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }

}