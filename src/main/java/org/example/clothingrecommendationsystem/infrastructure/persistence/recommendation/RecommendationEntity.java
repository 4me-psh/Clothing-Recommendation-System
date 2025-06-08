package org.example.clothingrecommendationsystem.infrastructure.persistence.recommendation;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity.BaseModelEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.generatedImage.GeneratedImageEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.person.PersonEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes.PieceOfClothesEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class RecommendationEntity extends BaseModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendation_seq")
    @SequenceGenerator(name = "recommendation_seq", sequenceName = "recommendation_seq", allocationSize = 1)
    private Long id;
    private String userPrompt;
    @ManyToOne
    private PersonEntity personEntity;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GeneratedImageEntity> generatedImageEntities;
    @ManyToMany
    private List<PieceOfClothesEntity> recommendedClothesEntities;
    private Boolean favorite;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date());
        this.setCreatedBy(this.getPersonEntity().getCreatedBy());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(this.getPersonEntity().getUpdatedBy());
    }

    public void setGeneratedImageEntities(List<GeneratedImageEntity> generatedImageEntities) {
        this.generatedImageEntities.clear();
        if (generatedImageEntities != null) {
            this.generatedImageEntities.addAll(generatedImageEntities);
        }
    }
}
