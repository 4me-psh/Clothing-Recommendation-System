package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import org.example.clothingrecommendationsystem.infrastructure.persistence.jparepositories.postgresql.PieceOfClothesRepository;
import org.example.clothingrecommendationsystem.model.pieceofclothes.IPieceOfClothesRepository;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PieceOfClothesRepositoryImpl implements IPieceOfClothesRepository {

    private final PieceOfClothesRepository pieceOfClothesRepository;
    private final ModelMapper modelMapper;

    @Lazy
    public PieceOfClothesRepositoryImpl(PieceOfClothesRepository pieceOfClothesRepository,
                                        @Qualifier("entityModelMapper") ModelMapper modelMapper) {
        this.pieceOfClothesRepository = pieceOfClothesRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<PieceOfClothes> getAll() {
        List<PieceOfClothes> pieces = new ArrayList<>();
        List<PieceOfClothesEntity> pieceEntities = pieceOfClothesRepository.findAll();
        for (PieceOfClothesEntity pieceEntity : pieceEntities) {
            pieces.add(modelMapper.map(pieceEntity, PieceOfClothes.class));
        }
        return pieces;
    }

    @Override
    public PieceOfClothes getById(Long id) {
        PieceOfClothesEntity pieceEntity = pieceOfClothesRepository.findById(id).orElse(null);
        return modelMapper.map(pieceEntity, PieceOfClothes.class);
    }

    @Override
    public PieceOfClothes create(PieceOfClothes entityToCreate) {
        PieceOfClothesEntity pieceEntity = modelMapper.map(entityToCreate, PieceOfClothesEntity.class);
        PieceOfClothesEntity createdPieceEntity = pieceOfClothesRepository.save(pieceEntity);
        return modelMapper.map(createdPieceEntity, PieceOfClothes.class);
    }

    @Override
    public PieceOfClothes edit(PieceOfClothes entityToUpdate) {
        PieceOfClothesEntity editedEntity = pieceOfClothesRepository.findById(entityToUpdate.getId()).orElse(null);
        assert editedEntity != null;
        modelMapper.map(entityToUpdate, editedEntity);
        pieceOfClothesRepository.save(editedEntity);
        return modelMapper.map(editedEntity, PieceOfClothes.class);
    }

    @Override
    public void delete(Long id) {
        PieceOfClothesEntity entity = pieceOfClothesRepository.findById(id).orElse(null);
        assert entity != null;
        pieceOfClothesRepository.delete(entity);
    }

    @Override
    public List<PieceOfClothes> getAllByUserId(Long id) {
        List<PieceOfClothes> pieces = new ArrayList<>();
        List<PieceOfClothesEntity> pieceEntities = pieceOfClothesRepository.findAllByUserEntity_Id(id);
        for (PieceOfClothesEntity pieceEntity : pieceEntities) {
            pieces.add(modelMapper.map(pieceEntity, PieceOfClothes.class));
        }
        return pieces;
    }
}
