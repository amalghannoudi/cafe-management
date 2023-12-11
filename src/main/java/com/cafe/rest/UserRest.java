package com.cafe.rest;

import com.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
   public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path="/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path="/getUsers")
    public ResponseEntity<List<UserWrapper>> getAllUser() ;

    @PostMapping(path="/updateStatus")
    public ResponseEntity<String> updateStatus (@RequestBody(required = true) Map<String,String> requestMap) ;

    @GetMapping(path="/checkToken")
    public ResponseEntity<String> checkToken() ;

    @PostMapping(path= "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap) ;

    @PostMapping(path = "/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap) ;
}