package org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.model.basemodel.BaseModel;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class BaseModelEntity extends BaseModel {

}
