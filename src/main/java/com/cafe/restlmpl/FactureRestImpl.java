package com.cafe.restlmpl;

import com.cafe.constantes.CafeConstants;
import com.cafe.models.Facture;
import com.cafe.rest.FactureRest;
import com.cafe.services.FactureService;
import com.cafe.utils.cafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FactureRestImpl implements FactureRest {

    @Autowired
   FactureService factureService ;

    @Override
    public ResponseEntity<String> generateFacture(Map<String, Object> requestMap) {

        try{
            return factureService.generateFacture(requestMap) ;
        }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Facture>> getFacture() {
        try{
            return factureService.getFacture() ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return  factureService.getPdf(requestMap) ;
        }catch (Exception e){
            e.printStackTrace();
    }
        return  null ;
}
}
