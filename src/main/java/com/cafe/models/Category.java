package com.cafe.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/* select categoris that conatins products */
@NamedQuery(name = "Category.getAllCategory", query= "select c from Category c where c.id in (select p.category from Product p where p.status='true')")

@Data
@Entity
@Table(name="category")

public class Category implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @Column(name="name")
    private String name ;

}
