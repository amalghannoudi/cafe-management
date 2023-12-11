package com.cafe.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name="Product.getAllPrd",query="select new com.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.prix,p.status,p.category.id,p.category.name) from Product p ")

@NamedQuery(name = "Product.updateStatus",query = "update Product p set p.status =: status where p.id =:id")

@NamedQuery(name = "Product.getByCat", query = "select new com.cafe.wrapper.ProductWrapper(p.id, p.name) from Product p where p.category.id = :id and p.status = 'true'")

@NamedQuery(name="Product.getProductById",query="select new com.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.prix) from Product p where p.id =:id")

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @Column(name = "name")
    private  String name ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category",nullable = false)
    private  Category category;

    @Column(name = "description")
    private String description ;

    @Column(name = "prix")
    private Integer prix ;

    @Column(name="status")
    private String status ;

}
