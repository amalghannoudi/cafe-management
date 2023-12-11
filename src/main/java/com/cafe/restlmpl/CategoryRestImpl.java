package com.cafe.restlmpl;

import com.cafe.constantes.CafeConstants;
import com.cafe.models.Category;
import com.cafe.rest.CategoryRest;
import com.cafe.services.CategoryService;
import com.cafe.utils.cafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestMap) {
        try {
            return categoryService.addCategory(requestMap) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCat(String filter) {
        try {
            return categoryService.getAllCat(filter) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(requestMap) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Integer id) {
        try {
            return categoryService.deleteCategory(id) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);    }

}

