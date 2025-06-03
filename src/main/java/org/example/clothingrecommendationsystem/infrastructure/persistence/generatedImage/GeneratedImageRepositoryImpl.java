package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;


import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.GeneratedImageRepository;
import org.example.clothingrecommendationsystem.model.generatedimage.GeneratedImage;
import org.example.clothingrecommendationsystem.model.generatedimage.IGeneratedImageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GeneratedImageRepositoryImpl implements IGeneratedImageRepository {

    private final GeneratedImageRepository generatedImageRepository;
    private final ModelMapper modelMapper;

    @Lazy
    public GeneratedImageRepositoryImpl(GeneratedImageRepository generatedImageRepository,
                                        @Qualifier("entityModelMapper") ModelMapper modelMapper) {
        this.generatedImageRepository = generatedImageRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<GeneratedImage> getAll() {
        List<GeneratedImage> pieces = new ArrayList<>();
        List<GeneratedImageEntity> pieceEntities = generatedImageRepository.findAll();
        for (GeneratedImageEntity pieceEntity : pieceEntities) {
            pieces.add(modelMapper.map(pieceEntity, GeneratedImage.class));
        }
        return pieces;
    }

    @Override
    public GeneratedImage getById(Long id) {
        GeneratedImageEntity pieceEntity = generatedImageRepository.findById(id).orElse(null);
        return modelMapper.map(pieceEntity, GeneratedImage.class);
    }

    @Override
    public GeneratedImage create(GeneratedImage entityToCreate) {
        GeneratedImageEntity pieceEntity = modelMapper.map(entityToCreate, GeneratedImageEntity.class);
        GeneratedImageEntity createdPieceEntity = generatedImageRepository.save(pieceEntity);
        return modelMapper.map(createdPieceEntity, GeneratedImage.class);
    }

    @Override
    public GeneratedImage edit(GeneratedImage entityToUpdate) {
        GeneratedImageEntity editedEntity = generatedImageRepository.findById(entityToUpdate.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToUpdate, editedEntity);
        generatedImageRepository.save(editedEntity);
        return modelMapper.map(editedEntity, GeneratedImage.class);
    }

    @Override
    public void delete(Long id) {
        GeneratedImageEntity entity = generatedImageRepository.findById(id).orElse(null);
        assert entity != null;
        generatedImageRepository.delete(entity);
    }

}
