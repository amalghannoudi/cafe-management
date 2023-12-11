package com.cafe.dao;

import com.cafe.models.Product;
import com.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {

  List<ProductWrapper> getAllPrd() ;

  @Modifying
  @Transactional
 Integer updateStatus(@Param("status")String status,@Param("id") Integer id) ;

    List<ProductWrapper> getByCat(@Param("id") Integer id);

    ProductWrapper getProductById(@Param("id")Integer id);
}
