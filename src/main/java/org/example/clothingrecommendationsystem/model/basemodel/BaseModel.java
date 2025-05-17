package org.example.clothingrecommendationsystem.model.basemodel;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseModel {
    protected Date createdAt;
    protected Date updatedAt;
    protected String createdBy;
    protected String updatedBy;
}
