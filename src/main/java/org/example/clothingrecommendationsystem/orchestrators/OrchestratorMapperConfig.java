package org.example.clothingrecommendationsystem.orchestrators;

import org.example.clothingrecommendationsystem.model.person.Person;
import org.example.clothingrecommendationsystem.model.recommendation.Recommendation;
import org.example.clothingrecommendationsystem.model.user.User;
import org.example.clothingrecommendationsystem.orchestrators.person.dto.CreatePersonDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.CreateRecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.recommendation.dto.RecommendationDto;
import org.example.clothingrecommendationsystem.orchestrators.user.dto.CreateUserDto;
import org.example.clothingrecommendationsystem.orchestrators.user.dto.EditUserDto;
import org.example.clothingrecommendationsystem.orchestrators.user.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrchestratorMapperConfig {

    @Bean(name = "orchestratorModelMapper")
    public ModelMapper modelMapperOrchestrator() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE).setCollectionsMergeEnabled(false);

        modelMapper.createTypeMap(User.class, UserDto.class);

        modelMapper.addMappings(new PropertyMap<CreateUserDto, User>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCreatedAt());
                skip(destination.getUpdatedAt());
                skip(destination.getCreatedBy());
                skip(destination.getUpdatedBy());

            }
        });

        modelMapper.addMappings(new PropertyMap<EditUserDto, User>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCreatedAt());
                skip(destination.getUpdatedAt());
                skip(destination.getCreatedBy());
                skip(destination.getUpdatedBy());

            }
        });

        modelMapper.addMappings(new PropertyMap<CreatePersonDto, Person>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCreatedAt());
                skip(destination.getUpdatedAt());
                skip(destination.getCreatedBy());
                skip(destination.getUpdatedBy());
                map().getUser().setId(source.getUserId());

            }
        });

        modelMapper.addMappings(new PropertyMap<CreateRecommendationDto, Recommendation>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getCreatedAt());
                skip(destination.getUpdatedAt());
                skip(destination.getCreatedBy());
                skip(destination.getUpdatedBy());


            }
        });

        modelMapper.addMappings(new PropertyMap<Recommendation, RecommendationDto>() {
            @Override
            protected void configure() {
                map().setPersonId(source.getPerson().getId());

            }
        });

        return modelMapper;
    }
}
