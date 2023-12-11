package com.cafe.dao;

import com.cafe.models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureDao extends JpaRepository<Facture,Integer> {
    List<Facture> getAllFacture();
    List<Facture> getFactureByUserName(@Param("username") String curretUser);
}
