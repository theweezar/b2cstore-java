package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
public class Category extends BaseEntity {

    @Id
    @Column(name = "category_id")
    private String categoryId;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryAssignment> assignments = new HashSet<>();

    public Category() {
    }

    public Category(String id, String name, String description) {
        setId(id);
        this.name = name;
        this.description = description;
    }

    public void setId(String id) {
        if (id != null) {
            this.categoryId = id.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        } else {
            this.categoryId = null;
        }
    }

    @Override
    public String toString() {
        return String.format("Category[id=%s, name='%s', description=%s]", categoryId, name, description);
    }

}
