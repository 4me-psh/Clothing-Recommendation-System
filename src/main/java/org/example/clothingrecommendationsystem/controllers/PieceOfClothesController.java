package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesOrchestrator;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
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

//    @Operation(summary = "Get all Clothes", description = "Gets all Clothes")
//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<User> entities = userOrchestrator.getAll();
//        List<UserDto> userDtos = new ArrayList<>(List.of());
//        for (User entity : entities) {
//            userDtos.add(modelMapper.map(entity, UserDto.class));
//        }
//        return ResponseEntity.ok(userDtos);
//    }

    @Operation(summary = "Add a new Piece", description = "Adds a new Piece")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PieceDto> createPiece(@RequestParam("userId") Long userId,
                                                @RequestParam("file") MultipartFile file) {
        PieceOfClothes createdPiece = pieceOfClothesOrchestrator.handlePieceOfClothes(userId, file);
        PieceDto createdUPieceDto = modelMapper.map(createdPiece, PieceDto.class);
        return ResponseEntity.ok(createdUPieceDto);
    }

    @Operation(summary = "Get A Piece by Id", description = "Gets the Piece by its Id")
    @GetMapping("/{id}")
    public ResponseEntity<PieceDto> getPieceById(@PathVariable Long id) {
        PieceOfClothes entity = pieceOfClothesOrchestrator.getById(id);
        PieceDto pieceDto = modelMapper.map(entity, PieceDto.class);
        return ResponseEntity.ok(pieceDto);
    }
//
//    @Operation(summary = "Update User", description = "Updates the User by their Id")
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody EditUserDto userDto) {
//        User editEntity = userOrchestrator.getById(id);
//        modelMapper.map(userDto, editEntity);
//        userOrchestrator.edit(editEntity);
//        return ResponseEntity.ok("ok edit");
//    }
//
//    @Operation(summary = "Delete User", description = "Deletes the User by their Id")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
//        User entityToDelete = userOrchestrator.getById(id);
//        userOrchestrator.delete(id);
//        return ResponseEntity.ok("ok delete");
//    }




}
