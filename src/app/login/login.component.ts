import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SuccesDialogComponent } from '../dialog/succes-dialog/succes-dialog.component';
import { ForgotPassComponent } from '../forgot-pass/forgot-pass.component';
import { UserService } from '../service/user.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: any = FormGroup;
  email: string = '';
  password: string = '';
  responseMsg: any;
  showErrorDialog: boolean = false; // Declare the property here
  errorMessage: string = '';
  showForgotPassError: boolean = false;
  forgotPassErrorMessage: string = '';
  
  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: Router,
    private dialog: MatDialog,
    private ngxService: NgxUiLoaderService
  ) {
    this.loginForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]],
      password: [null, [Validators.required]]
    });
  }

  async login() {
    this.ngxService.start();
    var formData = this.loginForm.value;
    var data = {
      email: formData.email,
      password: formData.password
    };
    this.ngxService.stop();

    if (!formData.email || !formData.password) {
      this.errorMessage = "Les champs sont obligatoires";
    } else {
    try {
      const response: any = await this.userService.login(data).toPromise();
      localStorage.setItem('token', response.token);
      this.route.navigate(['/dashboard']);
    } catch (error: any) {
      this.errorMessage = error.error?.message ?? GlobalConstants.genericError;
    }
  }
  }// login.component.ts
// ...

handleForgotPass() {
  const dialogConfig = new MatDialogConfig();
  dialogConfig.width = '550px';

  // Open the ForgotPassComponent dialog
  const dialogRef = this.dialog.open(ForgotPassComponent, dialogConfig);

  // Handle the message returned from ForgotPassComponent
  dialogRef.afterClosed().subscribe((result: any) => {
    if (result && result.message) {
      this.forgotPassErrorMessage = result.message;
      this.showForgotPassError = true;

      // Check if the message indicates success
      if (result.success) {
        this.openSuccessDialog(result.message);
      }
    }
  });
}

// Function to open the success dialog
openSuccessDialog(message: string): void {
  const dialogRef = this.dialog.open(SuccesDialogComponent, {
    width: '350px',
    data: { message: message }
  });

  dialogRef.afterClosed().subscribe(() => {
    // Handle actions after the success dialog is closed (if needed)
  });
}

  }
  
  

