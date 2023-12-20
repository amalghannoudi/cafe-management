import { Component,Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { response } from 'express';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from '../service/user.service';
import { GlobalConstants } from '../shared/global-constants';
import {Dialog,DIALOG_DATA,DialogModule}from '@angular/cdk/dialog'
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
  
})
export class SignupComponent {
  signUpForm: any = FormGroup;
  email: string = '';
  name :string='';
  number : string='' ; 
  password: string = '';
  confi_password:string='';
  responseMsg: any;
  showErrorDialog: boolean = false;
  errorMessage: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: Router,
    private dialog: MatDialog,
    private ngxService: NgxUiLoaderService
  ) {
    this.signUpForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      number: [null, [Validators.required, Validators.pattern(GlobalConstants.contactRegex)]],
      password: [null, [Validators.required,Validators.pattern(GlobalConstants.passwordRegex)]],
      confi_password: [null, [Validators.required]]
    });
  }
  
validatePass(){
  const password = this.signUpForm.controls['password'].value;
  const confiPassword = this.signUpForm.controls['confi_password'].value;

  return confiPassword && confiPassword !== password;
}

async signUp(){
    this.ngxService.start();

    var formData = this.signUpForm.value;
    var data = {
      email: formData.email,
      name: formData.name,
      number: formData.number,
      password: formData.password
    };
   console.log(data);
   this.ngxService.stop();

   if (!formData.email || !formData.password || !formData.name || !formData.number) {
    this.errorMessage = "Les champs sont obligatoires";
  }
  else {
   try {
    const response: any = await this.userService.signUp(data).toPromise();
   console.log(response.message);
    this.route.navigate(['/login']);
  } catch (error: any) {
    this.errorMessage = error.error?.message ?? GlobalConstants.genericError;
      
  }
 
  }
 }
}