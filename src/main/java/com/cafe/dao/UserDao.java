package com.cafe.dao;

import com.cafe.models.User;
import com.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

        User findByEmail(@Param("email") String email) ;

        List<UserWrapper> getUsers() ;

        List<String>  getAllAdmin() ;


         /*pour garantir la cohérence des données:
          Si une exception se produit pendant l'exécution de la méthode,
           la transaction est annulée (rollback), ce qui signifie que toutes
           les modifications de base de données
           effectuées pendant cette transaction sont annulées
          */
        @Transactional
        /* pour modifier l'etat du BD(update , delete)*/
        @Modifying
        Integer UpdateStatus(@Param("status") String status , @Param("id") Integer id) ;

}
