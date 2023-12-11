package com.cafe.servicelmpl;

import com.cafe.JWT.CustomerUserDetailsService;
import com.cafe.JWT.JwtFilter;
import com.cafe.JWT.JwtUtil;
import com.cafe.constantes.CafeConstants;
import com.cafe.dao.UserDao;
import com.cafe.models.User;
import com.cafe.services.UserService;
import com.cafe.utils.EmailUtils;
import com.cafe.utils.cafeUtils;
import com.cafe.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.google.common.base.Strings;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  @Autowired
    UserDao userDao ;

    @Autowired
    AuthenticationManager authenticationManager ;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService ;

    @Autowired
    JwtUtil jwtUtil ;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils ;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
     log.info("Inside signup{}",requestMap);
   try {
       if (validateSignUpMap(requestMap)) {
           User user = userDao.findByEmail(requestMap.get("email"));
           if (Objects.isNull(user)) {
               userDao.save(getUserInfo(requestMap));
               return cafeUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
           } else {
               return cafeUtils.getResponseEntity("Email already exits", HttpStatus.BAD_REQUEST);
           }
       } else {
           return cafeUtils.getResponseEntity(CafeConstants.Invalid_data, HttpStatus.BAD_REQUEST);
       }
   }catch (Exception e){
       log.error("Error during registration",  e.getMessage());

       e.printStackTrace();
   }
   return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR) ;
    }



    private boolean validateSignUpMap(Map<String,String> requestMap){
        if( requestMap.containsKey("name") && requestMap.containsKey("number")
          && requestMap.containsKey("email") && requestMap.containsKey("password")){
     return true ;
         }
        return false ;
    }
    private User getUserInfo(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setNumber(requestMap.get("number"));
        user.setPassword(requestMap.get("password"));
        // Set default values
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            /* si l'authenfication est reussite */
            if (auth.isAuthenticated()) {
                String status = customerUserDetailsService.getUserDetails().getStatus();
                /* si user est activé */
                if (status != null && status.equalsIgnoreCase("true")) {
                    /* genere token et le renvoie */
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
                                    customerUserDetailsService.getUserDetails().getRole()) + "\"}",
                            HttpStatus.OK);
                }
                return new ResponseEntity<String>("{\"message\":\"Wait for admin approval\"}", HttpStatus.BAD_REQUEST);

            }
        } catch (BadCredentialsException ex) {
            // Si le mot de passe est incorrect
            return new ResponseEntity<String>("{\"message\":\"Mot de passe incorrect\"}", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {

        }
        return new ResponseEntity<String>("{\"message\":\"Erreur\"}", HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try{
         if (jwtFilter.isAdmin()){
           return new ResponseEntity<>(userDao.getUsers(),HttpStatus.OK) ;
         }else
             return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
    try {
        if (jwtFilter.isAdmin()){
          Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
                 if (!optional.isEmpty()){
                     userDao.UpdateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAdmin(requestMap.get("status"),optional.get().getEmail(),userDao.getAllAdmin());
                    return cafeUtils.getResponseEntity("User status updated Successufly",HttpStatus.OK) ;
                 }
                 else
                    return cafeUtils.getResponseEntity("User doesn't exist",HttpStatus.OK) ;

        }else
            return  cafeUtils.getResponseEntity(CafeConstants.Unathorized,HttpStatus.UNAUTHORIZED) ;
    }catch(Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR) ;


    }



    private void sendMailToAdmin(String status, String user, List<String> allAdmin) {
         allAdmin.remove(jwtFilter.getCurretUser());
         if (status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMsg(jwtFilter.getCurretUser(),"Account approuved","User:- "+user+"\n is approuved by \n ADMIN:-"+jwtFilter.getCurretUser(), allAdmin);

         }else {
             emailUtils.sendSimpleMsg(jwtFilter.getCurretUser(),"Account disabled","User:- "+user+"\n is disbaled by \n ADMIN:-"+jwtFilter.getCurretUser(), allAdmin);

         }
    }

    @Override
    public ResponseEntity<String> checkToken() {
         return  cafeUtils.getResponseEntity("true",HttpStatus.OK) ;

    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            User user= userDao.findByEmail(jwtFilter.getCurretUser());
            if (!user.equals(null)){
                if (user.getPassword().equals(requestMap.get("oldPassword"))){
                   user.setPassword(requestMap.get("newPassword"));
                   userDao.save(user) ;
                   return cafeUtils.getResponseEntity("password updated successfuly",HttpStatus.OK) ;
                }
                return  cafeUtils.getResponseEntity("incorrect old password",HttpStatus.BAD_REQUEST) ;
            }
            return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR) ;
        }catch
        (Exception e){
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur,HttpStatus.INTERNAL_SERVER_ERROR) ;


    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
              emailUtils.forgotMail(user.getEmail(),"Credentials by Cafe System",user.getPassword());
            }
            return cafeUtils.getResponseEntity("Check your email, please.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cafeUtils.getResponseEntity(CafeConstants.erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
