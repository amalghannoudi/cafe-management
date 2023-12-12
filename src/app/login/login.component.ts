import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from '../service/user.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'] // Fix the property name to styleUrls
})
export  class LoginComponent {
   loginForm:any=FormGroup   ; 
 email :string ='';
 password : string='';
  responseMsg:any ; 
  constructor(private formBuilder : FormBuilder , 
    private userService:UserService,
    private route : Router,private ngxService:NgxUiLoaderService){
     
      this.loginForm =this.formBuilder.group({
        email:[null,[Validators.required,Validators.pattern(GlobalConstants.emailRegex)]],
       password:[null,[Validators.required]]
      })
  }
  
 login(){
   this.ngxService.start() ; 
   var formData =this.loginForm.value ; 
   var data ={
    email : formData.email, 
    password: formData.password
   }
   this.userService.login(data).subscribe((
    response:any)=>{
      this.ngxService.stop(); 
      localStorage.setItem('token',response.token); 
      this.route.navigate(['/dashboard']); 
    },(error)=>{
      if(error.error?.message){
        this.responseMsg=error.error?.message; 
      }else{
        this.responseMsg=GlobalConstants.genericError ; 
      }
    })
 }
  
}
