package com.veigadealmeida.projetofinal.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;

/**
 * Base entity with common fields
 *
 * @author gmiranda
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Version
    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public void setCadastro(BaseEntity entity) {
        Date now = new Date();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setVersion(1L);
    }

    public void setUpdate(BaseEntity entity) {
        Date now = new Date();
        entity.setUpdatedAt(now);
        entity.setVersion(entity.getVersion() + 1);
    }
}