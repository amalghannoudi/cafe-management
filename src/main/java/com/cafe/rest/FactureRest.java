package com.cafe.rest;

import com.cafe.models.Facture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/facture")
public interface FactureRest {

    @PostMapping(path="/generateFacture")
    ResponseEntity<String> generateFacture(@RequestBody Map<String,Object> requestMap) ;

    @GetMapping(path="/getF")
    ResponseEntity<List<Facture>> getFacture() ;

    @PostMapping(path="/getPdf")
    ResponseEntity<byte[]> getPdf (@RequestBody Map<String,Object> requestMap) ;

    @PostMapping(path="/delete/{id}")
    ResponseEntity<String> deleteById(@PathVariable Integer id );



}


