package com.cafe.rest;

import com.cafe.models.Category;
import com.cafe.restlmpl.CategoryRestImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path ="/category")
public interface CategoryRest  {

    @PostMapping(path ="/addC")
    ResponseEntity<String> addCategory(@RequestBody(required = true) Map<String,String> requestMap) ;

    @GetMapping(path = "/getC")
    ResponseEntity<List<Category>> getAllCat (@RequestParam(required = false) String filter) ;

    @PostMapping(path="/updateC")
    ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String,String> requestMap) ;

    @DeleteMapping(path="/deleteC/{id}")
    ResponseEntity<String> deleteCategory(@PathVariable Integer id) ;


}
