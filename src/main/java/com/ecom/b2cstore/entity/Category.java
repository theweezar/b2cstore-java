package com.ecom.b2cstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Category extends BaseEntity {

    @Id
    @Column(name = "category_id")
    @Getter
    private String categoryId;

    @Getter
    @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Getter
    @Setter
    private Category parent;

    @OneToMany(mappedBy = "category")
    private Set<CategoryAssignment> assignments = new HashSet<>();

    public void setId(String id) {
        if (id != null) {
            this.categoryId = id.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
        } else {
            this.categoryId = null;
        }
    }

    @Override
    public String toString() {
        return String.format("Category[id=%s, name='%s', parentId=%s]", categoryId, name,
                parent != null ? parent.getCategoryId() : null);
    }

    public Set<CategoryAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<CategoryAssignment> assignments) {
        this.assignments = assignments;
    }

}
