package org.example.clothingrecommendationsystem.orchestrators.person.dto;

import lombok.Data;
import org.example.clothingrecommendationsystem.model.person.Person;

@Data
public class CreatePersonDto {
    private Long userId;
    private Person.Gender gender;
    private Person.SkinTone skinTone;
    private String hairColor;
    private double height;
    private int age;
    private String pathToPerson;
}
