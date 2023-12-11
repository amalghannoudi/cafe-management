package com.cafe.JWT;

import com.cafe.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService
{
    @Autowired
    UserDao userDao ;

    private com.cafe.models.User userDetails ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername  {} ",email);
        userDetails =userDao.findByEmail(email) ;
        if (!Objects.isNull(userDetails))
            return  new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>()) ;
        else
            throw  new UsernameNotFoundException("User not found!") ;
    }

    public com.cafe.models.User getUserDetails() {

        return  userDetails ;
    }
}
