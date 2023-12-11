package com.cafe.servicelmpl;

import com.cafe.JWT.JwtFilter;
import com.cafe.constantes.CafeConstants;
import com.cafe.dao.ProductDao;
import com.cafe.models.Category;
import com.cafe.models.Product;
import com.cafe.services.ProductService;
import com.cafe.utils.cafeUtils;
import com.cafe.wrapper.ProductWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ProductDao productDao ;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
    try{
        if(jwtFilter.isAdmin()){
            if(validateProductMap(requestMap,false)){
               productDao.save(getProductFromMap(requestMap,false));
                return cafeUtils.getResponseEntity("addded successufly",HttpStatus.OK);
            }
            return cafeUtils.getResponseEntity(CafeConstants.Invalid_data,HttpStatus.BAD_REQUEST);
        }else{
           return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED);
        }

    }catch(Exception e){
        e.printStackTrace();
    }
    return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllPrd() {
        try{
            return new ResponseEntity<>(productDao.getAllPrd(),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if (jwtFilter.isAdmin()){
                if(validateProductMap(requestMap,true)){
                      Optional<Product> optional= productDao.findById(Integer.parseInt(requestMap.get("id")));
                   if (!optional.isEmpty()) {
                       Product product =getProductFromMap(requestMap,true);
                       product.setStatus(optional.get().getStatus());
                       productDao.save(product);
                       return cafeUtils.getResponseEntity("Product updated successfully",HttpStatus.OK);
                   }else {
                       return cafeUtils.getResponseEntity("product id doesn't not exist",HttpStatus.BAD_REQUEST);
                   }
                }
                return cafeUtils.getResponseEntity(CafeConstants.Invalid_data,HttpStatus.BAD_REQUEST);
            }else {
                return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            if (jwtFilter.isAdmin()){
                Optional optional=productDao.findById(id);
                    if (!optional.isEmpty()){
                        productDao.deleteById(id);
                        return cafeUtils.getResponseEntity("product deleted successfuly",HttpStatus.OK);
                    }else{
                      return  cafeUtils.getResponseEntity("product id doesn't not exist",HttpStatus.OK);
                    }
            }else {
                return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
       try{
          if (jwtFilter.isAdmin()){
            Optional optional=productDao.findById(Integer.parseInt(requestMap.get("id")));
          if (!optional.isEmpty()){
              productDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id"))) ;
            return cafeUtils.getResponseEntity("product status updated successfully",HttpStatus.OK);
          }else {
              return  cafeUtils.getResponseEntity("product id doesn't not exist",HttpStatus.OK);

          }
          } else {
              return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED);
          }
       }catch(Exception e){
           e.printStackTrace();
       }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCat(Integer id) {
        try{
            log.info("Executing getByCat method with category ID: {}", id);

            List<ProductWrapper> result = productDao.getByCat(id);

            log.info("Query result size: {}", result.size());

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getById(Integer id) {
        try{
          return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true ;
            }
            else if (!validateId) {
                return true ;
            }
        }
        return false ;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        Product product = new Product();

        if (isAdd){
           product.setId(Integer.parseInt(requestMap.get("id")));
       }else {
            product.setStatus("true") ;
        }
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrix(Integer.parseInt(requestMap.get("prix")));
        return product ;


    }
}
