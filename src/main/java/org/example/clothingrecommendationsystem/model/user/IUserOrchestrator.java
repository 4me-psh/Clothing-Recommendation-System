package org.example.clothingrecommendationsystem.model.user;

import java.util.List;

public interface IUserOrchestrator {
    List<User> getAll();
    User getById(Long id);
    User create(User entityToCreate);
    User edit(User entityToUpdate);
    User delete(Long id);
    User getByEmail(String email);
}
