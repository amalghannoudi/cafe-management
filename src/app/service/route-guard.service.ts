import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';
import {jwtDecode} from 'jwt-decode';
import { GlobalConstants } from '../shared/global-constants';
@Injectable({
  providedIn: 'root'
})
/* route-guard : controler les naviagtion entre les routes en fct des conditions*/
export class RouteGuardService {

  constructor(public auth:AuthService,
    public router:Router) { }

    canActivate(route:ActivatedRouteSnapshot):boolean{
      let expectedRoleArray: string[] = route.data['expectedRole'];

      const token:any=localStorage.getItem('token'); 
      var tokenPayload:any ; 
      try{
        tokenPayload =jwtDecode(token); 
      }
      catch (error) {
        localStorage.clear(); 
        this.router.navigate(['/login']); 
        return false; // Add a return statement here

      }
      let expectedRole='' ; 
      for (let i = 0 ; i<expectedRoleArray.length; i++){
        if (expectedRoleArray[i] == tokenPayload.role){
          expectedRole =tokenPayload.role ; 
        }
      }
      /* verifie le role et si il est authentifé il dirige vers /dashbord */
      if (tokenPayload.role == 'user' || tokenPayload.role =='admin'){
        if (this.auth.isAuth() && tokenPayload == expectedRole){
          return true ; 
        }
        console.log(GlobalConstants.unauthorized,GlobalConstants.error);
        this.router.navigate(['/dashboard']); 
        return false ; 
      }else {
        this.router.navigate(['/login']) ; 
        localStorage.clear(); 
        return false ; 

      }
    }
}
