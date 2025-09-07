package com.ecom.b2cstore.repository;

import com.ecom.b2cstore.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}