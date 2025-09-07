package com.ecom.b2cstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime creationDate;

    private LocalDateTime lastModified;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
        lastModified = creationDate;
    }

    @PreUpdate
    protected void onUpdate() {
        lastModified = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }
}
