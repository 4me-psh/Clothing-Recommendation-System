package org.example.clothingrecommendationsystem.orchestrators.person;

import org.example.clothingrecommendationsystem.model.person.IPersonOrchestrator;
import org.example.clothingrecommendationsystem.model.person.IPersonRepository;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.user.IUserRepository;
import org.example.clothingrecommendationsystem.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonOrchestrator implements IPersonOrchestrator {
    private final IPersonRepository personRepository;
    private final IUserRepository userRepository;

    @Autowired
    public PersonOrchestrator(IPersonRepository personRepository, IUserRepository userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
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
        personRepository.delete(id);
        return existingPerson;
    }

    @Override
    public Person getByUserId(Long userId) {
        return personRepository.getByUserId(userId);
    }
}
