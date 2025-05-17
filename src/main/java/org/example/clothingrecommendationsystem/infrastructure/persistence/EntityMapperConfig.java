package org.example.clothingrecommendationsystem.infrastructure.persistence;

import org.example.clothingrecommendationsystem.infrastructure.persistence.person.PersonEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes.PieceOfClothesEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.user.UserEntity;
import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.example.clothingrecommendationsystem.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityMapperConfig {

    @Bean("entityModelMapper")
    public ModelMapper modelMapperEntity() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        //User Mapping
        modelMapper.createTypeMap(User.class, UserEntity.class);
        modelMapper.createTypeMap(UserEntity.class, User.class);

        //Person Mapping
        modelMapper.typeMap(Person.class, PersonEntity.class).addMappings(mapper -> {
            mapper.map(Person::getUser,
                    PersonEntity::setUserEntity);
        });

        //PieceOfClothesMapping
        modelMapper.typeMap(PieceOfClothes.class, PieceOfClothesEntity.class).addMappings(mapper -> {
            mapper.map(PieceOfClothes::getUser,
                    PieceOfClothesEntity::setUserEntity);
        });

        return modelMapper;
    }
}

//modelMapper.addMappings(new PropertyMap<CreateUserDto, User>() {
//    @Override
//    protected void configure() {
//        skip(destination.getId());
//        skip(destination.getCreatedAt());
//
//    }
//});
//
//        modelMapper.addMappings(new PropertyMap<EditUserDto, User>() {
//    @Override
//    protected void configure() {
//        skip(destination.getId());
//        skip(destination.getCreatedAt());
//
//    }
//});