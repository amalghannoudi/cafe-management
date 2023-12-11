package com.cafe.services;

import com.cafe.models.Facture;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface FactureService {
    @Transactional
    ResponseEntity<String> generateFacture(Map<String, Object> requestMap);

    ResponseEntity<List<Facture>> getFacture();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    ResponseEntity<String> deleteById(Integer id);
}
