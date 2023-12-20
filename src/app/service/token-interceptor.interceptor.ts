import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

@Injectable()
/*cet intercepteur sert à ajouter automatiquement un en-tête
 d'autorisation aux requêtes
 sortantes en utilisant un jeton d'authentification stocké localemet*/
 
export class TokenInterceptor implements HttpInterceptor {

 constructor(private router : Router){

 }
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (typeof localStorage !== 'undefined') {
      const token = localStorage.getItem('token');
      if (token) {
        req = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` }
        });
      }
    }
   
    return next.handle(req).pipe(
      catchError((err)=>{
        if( err instanceof HttpErrorResponse){
          console.log(err.url) ; 
          if (err.status ===401 ||err.status ===403){
            if(this.router.url === '/login'){}
            else {
              localStorage.clear() ; 
              this.router.navigate(['/login']); 
            }
          }
        }
        return throwError(err); 
      })
    )
  }
}
