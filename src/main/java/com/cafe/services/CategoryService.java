package com.cafe.services;

import com.cafe.models.Category;
import com.cafe.servicelmpl.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService  {

    ResponseEntity<String> addCategory(Map<String, String> requestMap);

    ResponseEntity<List<Category>> getAllCat(String filter);

    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

    ResponseEntity<String> deleteCategory(Integer id);
}
