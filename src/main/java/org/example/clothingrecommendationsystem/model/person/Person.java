package org.example.clothingrecommendationsystem.model.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;
import org.example.clothingrecommendationsystem.model.user.User;

@EqualsAndHashCode(callSuper = true)
@Data
public class Person extends BaseModel {
    private Long id;
    private User user;
    private Gender gender;
    private SkinTone skinTone;
    private String hairColor;
    private double height;
    private int age;

    public enum Gender {
        MALE, FEMALE
    }
    public enum SkinTone{

        LIGHT, MEDIUMLIGHT, MEDIUM, MEDIUMDARK, DARK
    }
}
