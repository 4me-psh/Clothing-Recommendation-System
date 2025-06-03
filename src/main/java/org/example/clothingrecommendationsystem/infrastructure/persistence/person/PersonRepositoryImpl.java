package org.example.clothingrecommendationsystem.infrastructure.persistence.person;

import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.PersonRepository;
import org.example.clothingrecommendationsystem.model.person.IPersonRepository;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements IPersonRepository {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Lazy
    public PersonRepositoryImpl(PersonRepository personRepository,
                                @Qualifier("entityModelMapper") ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        List<PersonEntity> personEntities = personRepository.findAll();
        for (PersonEntity personEntity : personEntities) {
            persons.add(modelMapper.map(personEntity, Person.class));
        }
        return persons;
    }

    @Override
    public Person getById(Long id) {
        PersonEntity personEntity = personRepository.findById(id).orElse(null);
        return modelMapper.map(personEntity, Person.class);
    }

    @Override
    public Person create(Person entityToCreate) {
        PersonEntity personEntity = modelMapper.map(entityToCreate, PersonEntity.class);
        PersonEntity createdPersonEntity = personRepository.save(personEntity);
        return modelMapper.map(createdPersonEntity, Person.class);
    }

    @Override
    public Person edit(Person entityToUpdate) {
        PersonEntity editedEntity = personRepository.findById(entityToUpdate.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToUpdate, editedEntity);
        personRepository.save(editedEntity);
        return modelMapper.map(editedEntity, Person.class);
    }

    @Override
    public void delete(Long id) {
        PersonEntity entity = personRepository.findById(id).orElse(null);
        assert entity != null;
        personRepository.delete(entity);
    }

    @Override
    public Person getByUserId(Long userId) {
        PersonEntity personEntity = personRepository.findById(userId).orElse(null);
        assert personEntity != null;
        return modelMapper.map(personEntity, Person.class);
    }
}
