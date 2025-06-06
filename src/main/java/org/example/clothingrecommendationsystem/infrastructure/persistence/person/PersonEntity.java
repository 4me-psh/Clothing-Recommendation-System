package org.example.clothingrecommendationsystem.infrastructure.persistence.person;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity.BaseModelEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.user.UserEntity;
import org.example.clothingrecommendationsystem.model.person.Person;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PersonEntity extends BaseModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    private Long id;
    private Person.Gender gender;
    private Person.SkinTone skinTone;
    private String hairColor;
    private double height;
    private int age;
    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;
    private String pathToPerson;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(this.getUserEntity().getCreatedAt());
        this.setCreatedBy(this.getUserEntity().getEmail());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(this.getUserEntity().getUpdatedAt());
        this.setUpdatedBy(this.getUserEntity().getEmail());
    }
}
