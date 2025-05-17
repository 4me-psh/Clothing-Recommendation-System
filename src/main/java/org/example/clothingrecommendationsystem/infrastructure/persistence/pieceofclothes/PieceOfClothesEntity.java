package org.example.clothingrecommendationsystem.infrastructure.persistence.pieceofclothes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity.BaseModelEntity;
import org.example.clothingrecommendationsystem.infrastructure.persistence.user.UserEntity;
import org.example.clothingrecommendationsystem.model.pieceofclothes.PieceOfClothes;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PieceOfClothesEntity extends BaseModelEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "piece_seq")
    @SequenceGenerator(name = "piece_seq", sequenceName = "piece_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String color;
    private String material;
    private PieceOfClothes.Style style;
    private String pathToPhoto;
    private String pieceCategory;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "clothing_temperature_categories", joinColumns = @JoinColumn(name = "clothing_id"))
    @Enumerated(EnumType.STRING)
    private List<PieceOfClothes.TemperatureCategory> temperatureCategories;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "clothing_characteristics", joinColumns = @JoinColumn(name = "clothing_id"))
    private List<String> characteristics;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date());
        this.setCreatedBy(this.getUserEntity().getEmail());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(this.getUserEntity().getEmail());
    }
}
