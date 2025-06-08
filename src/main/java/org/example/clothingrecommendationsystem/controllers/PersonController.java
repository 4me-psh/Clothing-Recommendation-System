package org.example.clothingrecommendationsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clothingrecommendationsystem.model.person.IPersonOrchestrator;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.orchestrators.person.dto.CreatePersonDto;
import org.example.clothingrecommendationsystem.orchestrators.person.dto.EditPersonDto;
import org.example.clothingrecommendationsystem.orchestrators.person.dto.PersonDto;
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
@RequestMapping("/api/v1/persons")
@Tag(name = "Person", description = "Clothing Recommendation")
@CrossOrigin(origins={"http://localhost:8080"})
public class PersonController {

    private final IPersonOrchestrator personOrchestrator;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(IPersonOrchestrator personOrchestrator,
                            @Qualifier("orchestratorModelMapper") ModelMapper modelMapper) {
        this.personOrchestrator = personOrchestrator;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all Persons", description = "Gets all Persons")
    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<Person> entities = personOrchestrator.getAll();
        List<PersonDto> personDtos = new ArrayList<>(List.of());
        for (Person entity : entities) {
            personDtos.add(modelMapper.map(entity, PersonDto.class));
        }
        return ResponseEntity.ok(personDtos);
    }

    @Operation(summary = "Add a new Person", description = "Adds a new Person")
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<PersonDto> createPerson(@RequestBody CreatePersonDto personDto) {
        Person personToCreate = modelMapper.map(personDto, Person.class);
        Person createdPerson = personOrchestrator.create(personToCreate);
        PersonDto createdPersonDto = modelMapper.map(createdPerson, PersonDto.class);
        return ResponseEntity.ok(createdPersonDto);
    }

    @Operation(summary = "Get Person by Id", description = "Gets the Person by their Id")
    @GetMapping(path = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable Long id) {
        Person entity = personOrchestrator.getById(id);
        PersonDto personDto = modelMapper.map(entity, PersonDto.class);
        return ResponseEntity.ok(personDto);
    }

    @Operation(summary = "Update Person", description = "Updates the Person by their Id")
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable Long id, @RequestBody EditPersonDto personDto) {
        Person editEntity = personOrchestrator.getById(id);
        modelMapper.map(personDto, editEntity);
        personOrchestrator.edit(editEntity);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Delete Person", description = "Deletes the Person by their Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        Person entityToDelete = personOrchestrator.getById(id);
        personOrchestrator.delete(id);
        return ResponseEntity.ok("ok delete");
    }

    @Operation(summary = "Add a new Person with photo", description = "Adds a new Person photo")
    @PostMapping(path ="/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<PersonDto> createPersonWithPhoto(@RequestPart("personDto") CreatePersonDto personDto,
                                                           @RequestPart("file") MultipartFile file) {
        Person personToCreate = modelMapper.map(personDto, Person.class);
        Person createdPerson = personOrchestrator.createWithPhoto(personToCreate, file);
        PersonDto createdPersonDto = modelMapper.map(createdPerson, PersonDto.class);
        return ResponseEntity.ok(createdPersonDto);
    }

    @Operation(summary = "Update Person with Photo", description = "Updates the Person with Photo by their Id")
    @PutMapping(path ="/photo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updatePersonWithPhoto(@PathVariable Long id, @RequestPart("personDto") EditPersonDto personDto,
                                                        @RequestPart("file") MultipartFile file) {
        Person editEntity = personOrchestrator.getById(id);
        modelMapper.map(personDto, editEntity);
        personOrchestrator.editWithPhoto(editEntity, file);
        return ResponseEntity.ok("ok edit");
    }

    @Operation(summary = "Get Person by User Id", description = "Gets the Person by User Id")
    @GetMapping(path = "/user/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<PersonDto> getPersonByUserId(@PathVariable Long id) {
        Person entity = personOrchestrator.getByUserId(id);
        PersonDto personDto = modelMapper.map(entity, PersonDto.class);
        return ResponseEntity.ok(personDto);
    }

}

