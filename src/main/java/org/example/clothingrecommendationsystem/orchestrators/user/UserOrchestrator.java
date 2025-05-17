package org.example.clothingrecommendationsystem.orchestrators.user;

import org.example.clothingrecommendationsystem.model.user.IUserOrchestrator;
import org.example.clothingrecommendationsystem.model.user.IUserRepository;
import org.example.clothingrecommendationsystem.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrchestrator implements IUserOrchestrator {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserOrchestrator(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User create(User entityToCreate) {
        entityToCreate.setPassword(passwordEncoder.encode(entityToCreate.getPassword()));
        return userRepository.create(entityToCreate);
    }

    @Override
    public User edit(User entityToUpdate) {
        return userRepository.edit(entityToUpdate);
    }

    @Override
    public User delete(Long id) {
        User existingUser = getById(id);
        userRepository.delete(id);
        return existingUser;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
