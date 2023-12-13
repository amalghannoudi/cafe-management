import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from '../../env/env';
import { LoginComponent } from '../login/login.component';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url = env.apiUrl ; 

  constructor(private HttpClient:HttpClient) { 
  
  }

  login(data:any){
    return this.HttpClient.post(this.url+"/user/login",
    data,{
      headers:new HttpHeaders().set("Content-Type","application/json")
    })
  }
  signUp(data:any){
    return this.HttpClient.post(this.url+"/user/signup",
    data,{
      headers:new HttpHeaders().set("Content-Type","application/json")
    })
  }
}
