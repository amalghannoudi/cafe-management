import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryComponent } from './category/category.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FactureComponent } from './facture/facture.component';
import { LoginComponent } from './login/login.component';
import { ProductComponent } from './product/product.component';
import { RouteGuardService } from './service/route-guard.service';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SignupComponent } from './signup/signup.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect empty path to login
  { path: 'signup', component: SignupComponent },
  { path: 'dashboard/category', component: CategoryComponent },
  { path: 'dashboard/product', component: ProductComponent },
  { path: 'dashboard/user', component: UserComponent },
  { path: 'dashboard/facture', component: FactureComponent },
  { path: 'sidebar', component: SidebarComponent },
  {
    path: 'login',
    loadChildren: () => import('./login/login.component').then(m => m.LoginComponent),
    canActivate: [RouteGuardService],
    data: {
      expectedRole: ['admin', 'user']
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)], // Import LoginModule here
  exports: [RouterModule]
})
export class AppRoutingModule { }
