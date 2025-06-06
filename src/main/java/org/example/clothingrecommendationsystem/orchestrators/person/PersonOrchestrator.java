package org.example.clothingrecommendationsystem.orchestrators.person;

import org.example.clothingrecommendationsystem.infrastructure.persistence.PhotoHandler;
import org.example.clothingrecommendationsystem.model.person.IPersonOrchestrator;
import org.example.clothingrecommendationsystem.model.person.IPersonPhotoHandler;
import org.example.clothingrecommendationsystem.model.person.IPersonRepository;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PersonOrchestrator implements IPersonOrchestrator {
    private final IPersonRepository personRepository;
    private final IUserRepository userRepository;
    private final IPersonPhotoHandler personPhotoHandler;

    @Autowired
    public PersonOrchestrator(IPersonRepository personRepository, IUserRepository userRepository, IPersonPhotoHandler personPhotoHandler) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.personPhotoHandler = personPhotoHandler;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public Person getById(Long id) {
        return personRepository.getById(id);
    }

    @Override
    public Person create(Person entityToCreate) {
        entityToCreate.setUser(userRepository.getById(entityToCreate.getUser().getId()));
        return personRepository.create(entityToCreate);
    }

    @Override
    public Person edit(Person entityToUpdate) {
        return personRepository.edit(entityToUpdate);
    }

    @Override
    public Person delete(Long id) {
        Person existingPerson = getById(id);
        personPhotoHandler.deletePiecePhoto(existingPerson.getPathToPerson());
        personRepository.delete(id);
        return existingPerson;
    }

    @Override
    public Person getByUserId(Long userId) {
        return personRepository.getByUserId(userId);
    }

    @Override
    public Person createWithPhoto(Person entityToCreate, MultipartFile file) {
        String pathToPersonPhoto = personPhotoHandler.addPiecePhoto(file);
        entityToCreate.setPathToPerson(pathToPersonPhoto);
        return create(entityToCreate);
    }

    @Override
    public Person editWithPhoto(Person entityToUpdate, MultipartFile file) {
        personPhotoHandler.deletePiecePhoto(entityToUpdate.getPathToPerson());
        String pathToPersonPhoto = personPhotoHandler.addPiecePhoto(file);
        entityToUpdate.setPathToPerson(pathToPersonPhoto);
        return edit(entityToUpdate);
    }
}
