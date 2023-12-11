package com.cafe.servicelmpl;

import com.cafe.JWT.JwtFilter;
import com.cafe.constantes.CafeConstants;
import com.cafe.dao.CategoryDao;
import com.cafe.models.Category;
import com.cafe.services.CategoryService;
import com.cafe.utils.cafeUtils;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestMap) {
    try{
       if(jwtFilter.isAdmin()){
           /* dans cas d'ajout on n'a pas besoin du id c pourquoi on met false */
           if(validateCat(requestMap,false)){
               categoryDao.save(getCategoryFromMap(requestMap,false));
               return cafeUtils.getResponseEntity("Category added successfully",HttpStatus.OK);
           }
       } else{
          return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED) ;
       }
    }catch (Exception e){
        e.printStackTrace();
    }
    return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCat(String filter) {
        try{
            if (filter != null && filter.equalsIgnoreCase("true")){
               return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(),HttpStatus.OK) ;
            }
            return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try{
        if (jwtFilter.isAdmin()){
            if(validateCat(requestMap,true)){
              Optional optional= categoryDao.findById(Integer.parseInt(requestMap.get("id")));
              if (!optional.isEmpty()){
                 categoryDao.save(getCategoryFromMap(requestMap,true));
                 return cafeUtils.getResponseEntity("category updated successfully",HttpStatus.OK);
              }else{
                 return cafeUtils.getResponseEntity("Catgeory n'esxite pas ",HttpStatus.OK );
              }
            }
            return cafeUtils.getResponseEntity(CafeConstants.Invalid_data,HttpStatus.BAD_REQUEST);
        }else{
            cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED) ;
        }

        }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Integer id) {
        try{
          if (jwtFilter.isAdmin()){
             Optional optional= categoryDao.findById(id);
                if(!optional.isEmpty()){
                    categoryDao.deleteById(id);
                    return cafeUtils.getResponseEntity("Category deleted",HttpStatus.OK) ;
                }else {
                    return cafeUtils.getResponseEntity("Category n'existe pas",HttpStatus.OK);
                }
          }else {
              return cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED);
          }
        }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR);
    }
/* valideId parfois obligatoire parfois non cas update et delete obligatoire par contre non obligatoire in add */
    private boolean validateCat(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true ;
            }else if (!validateId) {
                return true ;
            }
            }
        return false ;
    }
    /* isAdd : true when only we add new category or we delete , it will extract l id from request map
    * dans le cas update l id n'est pas necessaire car on modify only name
    * */
    private Category getCategoryFromMap(Map <String,String> requestMap , Boolean isAdd){
        Category category = new Category();
        if (isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category ;
    }
}
