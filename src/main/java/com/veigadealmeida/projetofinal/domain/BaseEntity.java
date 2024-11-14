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

}