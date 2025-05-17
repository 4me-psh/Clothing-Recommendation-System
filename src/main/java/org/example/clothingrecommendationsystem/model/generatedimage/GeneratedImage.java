package org.example.clothingrecommendationsystem.model.generatedimage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;
import org.example.clothingrecommendationsystem.model.person.Person;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneratedImage extends BaseModel {
    private Long id;
    private Person person;
    private String pathToImage;
}
