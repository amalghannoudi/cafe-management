package com.cafe.models;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;


/*@Data : generate constructor and getters , setters */

@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
/* select information from user table and create a new instance of userWrapper using information (constructeur)*/
@NamedQuery(name= "User.getUsers", query= "SELECT new com.cafe.wrapper.UserWrapper(u.id,u.name,u.email,u.number,u.status) from User u where u.role='user' ")

@NamedQuery(name="User.UpdateStatus", query ="update User u set u.status=:status where u.id=:id")

@NamedQuery(name= "User.getAllAdmin", query= "SELECT u.email from User u where u.role='admin' ")

/* @Data : getters ans setters automaticly */
@Data
@Entity
@Table(name="User")

public class User implements Serializable {

    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @Column(name="name")
    private String name ;

    @Column(name="number")
    private String number ;

    @Column(name="email")
    private String email ;

    @Column(name="password")
    private String password ;

    @Column(name="status")
    private String status ;

    @Column(name="role")
    private String role  ;
}
