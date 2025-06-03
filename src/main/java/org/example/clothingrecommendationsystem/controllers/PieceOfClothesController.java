package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesOrchestrator;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto.EditPieceDto;
import org.example.clothingrecommendationsystem.orchestrators.pieceofclothes.dto.PieceDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clothes")
@Tag(name = "Piece Of Clothes", description = "Clothing Recommendation")
@CrossOrigin(origins={"http://localhost:8080"})
public class PieceOfClothesController {

    private final IPieceOfClothesOrchestrator pieceOfClothesOrchestrator;
    private final ModelMapper modelMapper;
    @Autowired
    public PieceOfClothesController(IPieceOfClothesOrchestrator pieceOfClothesOrchestrator,
                                    @Qualifier("orchestratorModelMapper") ModelMapper modelMapper) {
        this.pieceOfClothesOrchestrator = pieceOfClothesOrchestrator;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all Clothes", description = "Gets all Clothes")
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<PieceDto>> getAllPieces() {
        List<PieceOfClothes> entities = pieceOfClothesOrchestrator.getAll();
        List<PieceDto> pieceDtos = new ArrayList<>(List.of());
        for (PieceOfClothes entity : entities) {
            pieceDtos.add(modelMapper.map(entity, PieceDto.class));
        }
        return ResponseEntity.ok(pieceDtos);
    }

    @Operation(summary = "Add a new Piece", description = "Adds a new Piece")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<PieceDto> createPiece(@RequestParam("userId") Long userId,
                                                @RequestParam("file") MultipartFile file) {
        PieceOfClothes createdPiece = pieceOfClothesOrchestrator.handlePieceOfClothes(userId, file);
        PieceDto createdUPieceDto = modelMapper.map(createdPiece, PieceDto.class);
        return ResponseEntity.ok(createdUPieceDto);
    }

    @Operation(summary = "Get A Piece by Id", description = "Gets the Piece by its Id")
    @GetMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<PieceDto> getPieceById(@PathVariable Long id) {
        PieceOfClothes entity = pieceOfClothesOrchestrator.getById(id);
        PieceDto pieceDto = modelMapper.map(entity, PieceDto.class);
        return ResponseEntity.ok(pieceDto);
    }

    @Operation(summary = "Get All Pieces From User", description = "Gets All Pieces From a User by Id")
    @GetMapping(path = "/user/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<PieceDto>> getAllPiecesByUserId(@PathVariable Long id) {

        List<PieceOfClothes> entities = pieceOfClothesOrchestrator.getAllByUserId(id);
        List<PieceDto> pieceDtos = new ArrayList<>();
        for(PieceOfClothes piece : entities) {
            pieceDtos.add(modelMapper.map(piece, PieceDto.class));
        }
        return ResponseEntity.ok(pieceDtos);
    }

    @Operation(summary = "Update Piece", description = "Updates the Piece by its Id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePiece(@PathVariable Long id, @RequestBody EditPieceDto pieceDto) {
        PieceOfClothes editEntity = pieceOfClothesOrchestrator.getById(id);
        modelMapper.map(pieceDto, editEntity);
        pieceOfClothesOrchestrator.edit(editEntity);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Delete Piece", description = "Deletes the Piece by its Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePiece(@PathVariable Long id) {
        PieceOfClothes entityToDelete = pieceOfClothesOrchestrator.getById(id);
        pieceOfClothesOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }




}
