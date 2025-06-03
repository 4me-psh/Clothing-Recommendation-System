package org.example.clothingrecommendationsystem.model.person;

import java.util.List;

public interface IPersonOrchestrator {
    List<Person> getAll();
    Person getById(Long id);
    Person create(Person entityToCreate);
    Person edit(Person entityToUpdate);
    Person delete(Long id);
    Person getByUserId(Long userId);
}
