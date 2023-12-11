package com.cafe.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="Facture.getAllFacture", query ="select f from Facture f order by f.id desc")

@NamedQuery(name="Facture.getFactureByUserName",query ="select f from Facture f where f.createdBy =:username order by f.id desc")

@Data
@Entity
@Table(name = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @Column(name="uuid")
    private String uuid ;

    @Column(name="name")
    private String name ;

    @Column(name="email")
    private String email;

    @Column(name="number")
    private String number;

    @Column(name="methodepayement") /* data base */
    private String methodePayement; /* model */

    @Column (name="totale")
    private Integer totale ;

    @Column(name="productdetails",columnDefinition = "json")
    private String productDetails ;

    @Column(name="createdby")
    private String createdBy ;
}
