import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatInputModule } from '@angular/material/input'; // Import from '@angular/material/input'
import { NgxUiLoaderConfig, NgxUiLoaderModule, SPINNER } from 'ngx-ui-loader';
import { MatDialogModule } from '@angular/material/dialog';
import { ForgotPassComponent } from './forgot-pass/forgot-pass.component'
import { MatToolbarModule } from '@angular/material/toolbar'; 
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SuccesDialogComponent } from './dialog/succes-dialog/succes-dialog.component';
import { CategoryComponent } from './category/category.component';
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatListModule } from '@angular/material/list'; // Add this line
import { MatCardModule } from '@angular/material/card';
import { ProductComponent } from './product/product.component';
import { UserComponent } from './user/user.component';
import { FactureComponent } from './facture/facture.component';
import { MatTableModule } from '@angular/material/table';
import { TokenInterceptor } from './service/token-interceptor.interceptor';
import { ConfirmationComponent } from './dialog/succes-dialog/confirmation/confirmation.component';
import { ChangePassComponent } from './dialog/succes-dialog/change-pass/change-pass.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MenuItems } from './shared/MenuItems';

const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  text: "loading ...",
  textColor: "#FFFFFF",
  textPosition: "center-center",
  bgsColor: "#7b1fa2",
  fgsColor: "#7b1fa2",
  fgsType: SPINNER.squareJellyBox,
  fgsSize: 100,
  hasProgressBar: false
}

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidebarComponent,
    DashboardComponent,
    LoginComponent,
    SignupComponent,
    ForgotPassComponent,
    SuccesDialogComponent,
    CategoryComponent,
    ProductComponent,
    UserComponent,
    FactureComponent,
    ConfirmationComponent,
    ChangePassComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatTableModule,
    MatMenuModule,
    MatButtonModule,
    MatDialogModule,
    HttpClientModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
  ],
  providers: [
    provideClientHydration(),
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    MenuItems
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
