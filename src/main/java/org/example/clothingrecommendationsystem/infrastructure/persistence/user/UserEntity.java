package org.example.clothingrecommendationsystem.infrastructure.persistence.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.clothingrecommendationsystem.infrastructure.persistence.basemodelentity.BaseModelEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;

    @PrePersist
    protected void onCreate() {
        this.setCreatedAt(new Date());
        this.setCreatedBy(this.getEmail());
    }

    @PreUpdate
    protected void onUpdate() {
        this.setUpdatedAt(new Date());
        this.setUpdatedBy(this.getEmail());
    }

}
