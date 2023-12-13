import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
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

    try {
      const response: any = await this.userService.login(data).toPromise();
      this.ngxService.stop();
      localStorage.setItem('token', response.token);
      this.route.navigate(['/dashboard']);
    } catch (error: any) {
      this.ngxService.stop();
      this.errorMessage = error.error?.message ?? GlobalConstants.genericError;

    }
  }

 
}
