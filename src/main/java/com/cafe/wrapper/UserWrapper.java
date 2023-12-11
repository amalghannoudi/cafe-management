package com.cafe.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/* pour le constructeur par defaut */
@NoArgsConstructor
public class UserWrapper {

    private Integer id ;
    private String name ;
    private String email ;
    private String number ;
    private String status ;


    public UserWrapper(Integer id, String name, String email, String number, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.status = status;
    }
}
