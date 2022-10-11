package com.project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.demo.model.category.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

}
