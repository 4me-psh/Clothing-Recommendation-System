package org.example.clothingrecommendationsystem.model.user;

import java.util.List;

public interface IUserRepository {
    List<User> getAll();
    User getById(Long id);
    User create(User entityToCreate);
    User edit(User entityToUpdate);
    void delete(Long id);
    User getByEmail(String email);
}
