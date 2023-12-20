import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { response } from 'express';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from '../../../service/user.service';
import { GlobalConstants } from '../../../shared/global-constants';

@Component({
  selector: 'app-change-pass',
  templateUrl: './change-pass.component.html',
  styleUrl: './change-pass.component.css'
})
export class ChangePassComponent {

  oldPass = true ; 
  newPass = true ; 
  confirmPass =true ; 
  changePassForm :FormGroup; 
  responseMsg : any ; 
  showOldPassword = true;

  constructor(private formBuilder:FormBuilder,public  userService : UserService,
    public dialogRef :MatDialogRef<ChangePassComponent>,
    private ngxService:NgxUiLoaderService){

      this.changePassForm=this.formBuilder.group({
        oldPass :[null,Validators.required],
        newPass :[null,Validators.required],
        confirmPass :[null,Validators.required],

      })
  }

  validateSubmit() {  // Correction ici, changer le nom de la méthode en validateSubmit
    return this.changePassForm.controls['newPass'].value !== this.changePassForm.controls['confirmPass'].value;
  }
  toggleShowOldPassword() {
    this.showOldPassword = !this.showOldPassword;
  }
  handleChangePass(){
    this.ngxService.start(); 
    var formData= this.changePassForm.value ; 
    var data ={
      oldPass :formData.oldPass,
      newPass : formData.newPass,
      confirmPass : formData.confirmPass
    }
    this.userService.changePassword(data).subscribe((response:any)=>{
      this.ngxService.stop() ; 
      this.responseMsg=response?.message; 
      this.dialogRef.close(); 
    },(error)=>{
      this.ngxService.stop(); 
      if(error.error?.message){
        this.responseMsg=error.error?.message ; 
      }
      else{
        this.responseMsg =GlobalConstants.genericError; 
      }

    })
  }
}
