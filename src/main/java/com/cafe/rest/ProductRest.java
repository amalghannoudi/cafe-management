package com.cafe.rest;

import com.cafe.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/product")
public interface ProductRest {

    @PostMapping(path="/addP")
    ResponseEntity<String> addProduct (@RequestBody Map<String,String> requestMap) ;

    @GetMapping(path="/getP")
    ResponseEntity<List<ProductWrapper>> getAllPrd() ;

    @PostMapping(path="/updateP")
    ResponseEntity<String> updateProduct (@RequestBody Map<String,String> requestMap) ;

    @DeleteMapping(path="/deleteP/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id );

    @PostMapping(path="/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap) ;

    @GetMapping(path="/getByC/{id}")
    ResponseEntity<List<ProductWrapper>> getByCat(@PathVariable Integer id) ;

    @GetMapping(path="/getById/{id}")
    ResponseEntity<ProductWrapper> getById(@PathVariable Integer id );


}
