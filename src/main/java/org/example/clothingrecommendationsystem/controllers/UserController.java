package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.infrastructure.security.InternalOnly;
import org.example.clothingrecommendationsystem.model.user.*;
import org.example.clothingrecommendationsystem.orchestrators.user.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "Clothing Recommendation")
@CrossOrigin(origins={"http://localhost:8080"})
public class UserController {

    private final IUserOrchestrator userOrchestrator;
    private final ModelMapper modelMapper;
    @Autowired
    public UserController(IUserOrchestrator userOrchestrator,
                          @Qualifier("orchestratorModelMapper") ModelMapper modelMapper) {
        this.userOrchestrator = userOrchestrator;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all Users", description = "Gets all Users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> entities = userOrchestrator.getAll();
        List<UserDto> userDtos = new ArrayList<>(List.of());
        for (User entity : entities) {
            userDtos.add(modelMapper.map(entity, UserDto.class));
        }
        return ResponseEntity.ok(userDtos);
    }

    @Operation(summary = "Add a new User", description = "Adds a new User")
    @PostMapping
    @InternalOnly
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto userDto) {
        User userToCreate = modelMapper.map(userDto, User.class);
        User createdUser = userOrchestrator.create(userToCreate);
        UserDto createdUserDto = modelMapper.map(createdUser, UserDto.class);
        return ResponseEntity.ok(createdUserDto);
    }

    @Operation(summary = "Get User by Id", description = "Gets the User by their Id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User entity = userOrchestrator.getById(id);
        UserDto userDto = modelMapper.map(entity, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(summary = "Update User", description = "Updates the User by their Id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody EditUserDto userDto) {
        User editEntity = userOrchestrator.getById(id);
        modelMapper.map(userDto, editEntity);
        userOrchestrator.edit(editEntity);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Delete User", description = "Deletes the User by their Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User entityToDelete = userOrchestrator.getById(id);
        userOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }

    @Operation(summary = "Get User by Email", description = "Gets User by their Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        User entity = userOrchestrator.getByEmail(email);
        UserDto userDto = modelMapper.map(entity, UserDto.class);
        return ResponseEntity.ok(userDto);
    }



}
