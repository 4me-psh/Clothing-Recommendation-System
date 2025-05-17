package org.example.clothingrecommendationsystem.infrastructure.persistence.user;


import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.UserRepository;
import org.example.clothingrecommendationsystem.model.user.IUserRepository;
import org.example.clothingrecommendationsystem.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Lazy
    public UserRepositoryImpl(UserRepository userRepository,
                              @Qualifier("entityModelMapper") ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            users.add(modelMapper.map(userEntity, User.class));
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public User create(User entityToCreate) {
        UserEntity userEntity = modelMapper.map(entityToCreate, UserEntity.class);
        UserEntity createdUserEntity = userRepository.save(userEntity);
        return modelMapper.map(createdUserEntity, User.class);
    }

    @Override
    public User edit(User entityToUpdate) {
        UserEntity editedEntity = userRepository.findById(entityToUpdate.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToUpdate, editedEntity);
        userRepository.save(editedEntity);
        return modelMapper.map(editedEntity, User.class);
    }

    @Override
    public void delete(Long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        assert entity != null;
        userRepository.delete(entity);
    }

    @Override
    public User getByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);
        assert userEntity != null;
        return modelMapper.map(userEntity, User.class);
    }
}
