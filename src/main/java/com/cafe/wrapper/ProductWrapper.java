package com.cafe.wrapper;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data

public class ProductWrapper {
   Integer id ;
   String name ;
   String description ;
   Integer prix ;
   String status ;
   Integer categoryId ;
   String categoryName ;

   public ProductWrapper(){

   }
   public ProductWrapper(Integer id, String name , String description, Integer prix , String status , Integer categoryId , String categoryName){
      this.id=id ;
      this.name=name ;
      this.description=description;
      this.prix=prix ;
      this.status=status ;
      this.categoryId=categoryId;
      this.categoryName=categoryName;
   }
   public ProductWrapper(Integer id , String name){
      this.id=id ;
      this.name=name ;
   }
   public ProductWrapper(Integer id, String name , String description, Integer prix ){
      this.id=id ;
      this.name=name ;
      this.description=description;
      this.prix=prix ;

   }
}
