package org.example.clothingrecommendationsystem.model.generatedimage;

import java.util.List;

public interface IGeneratedImageRepository {
    List<GeneratedImage> getAll();
    GeneratedImage getById(Long id);
    GeneratedImage create(GeneratedImage entityToCreate);
    GeneratedImage edit(GeneratedImage entityToUpdate);
    void delete(Long id);
}
