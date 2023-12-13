import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { response } from 'express';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from '../service/user.service';
import { GlobalConstants } from '../shared/global-constants';

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
     password: [null, [Validators.required]],
    confirm_pass: [null, [Validators.required]]
    });
  }
validatePass(){
  if(this.signUpForm.controls['password'].value != this.signUpForm.controls['confirm_pass'].value){
    return true  ;
  }
  else return false ; 
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
   try {
    const response: any = await this.userService.signUp(data).toPromise();
   console.log(response.message);
    this.ngxService.stop();
    this.route.navigate(['/login']);
  } catch (error: any) {
    this.ngxService.stop();
    this.errorMessage = error.error?.message ?? GlobalConstants.genericError;

  }
 

 }
}
