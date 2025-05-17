package org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity.BaseModelEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.person.PersonEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class GeneratedImageEntity extends BaseModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generatedImage_seq")
    @SequenceGenerator(name = "generatedImage_seq", sequenceName = "generatedImage_seq", allocationSize = 1)
    private Long id;
    private String pathToImage;
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonEntity personEntity;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date());
        this.setCreatedBy(this.getPersonEntity().getUserEntity().getEmail());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(this.getPersonEntity().getUserEntity().getEmail());
    }
}
