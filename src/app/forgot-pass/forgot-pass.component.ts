import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { UserService } from '../service/user.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-forgot-pass',
  templateUrl: './forgot-pass.component.html',
  styleUrls: ['./forgot-pass.component.css'] // Correct property name
})
export class ForgotPassComponent {
   form_pass : any = FormGroup; 
   responseMsg : any ='' ; 
   constructor(private formBuilder:FormBuilder,
    private userService:UserService,
    public dialoRef:MatDialogRef<ForgotPassComponent>,
    private ngxService : NgxUiLoaderService){

      this.form_pass =this.formBuilder.group({
        email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
       }); 
    }
    handleSumbit(){
      this.ngxService.start() ; 
      var formData = this.form_pass.value ; 
      var data ={
        email : formData.email
      }
      this.userService.forgotPass(data).subscribe((response:any)=>{
        this.ngxService.stop() ;
        this.responseMsg=response.message; 

        this.dialoRef.close({ message: this.responseMsg, success: true }); 
      },(error)=>{
        this.ngxService.stop(); 
        this.dialoRef.close(); 

        if (error.error?.message){
          this.responseMsg=error.error?.message ; 
        }else {
          this.responseMsg=GlobalConstants.genericError; 
        }
      })

    }
    onNoClick(): void {
      this.dialoRef.close({ message: '' , success: false });
    }
  }
