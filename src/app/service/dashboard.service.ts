import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { env } from '../../env/env';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  url = env.apiUrl;
  token = localStorage.getItem("token");

  constructor(private httpClient: HttpClient) {}

  getDetails() {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token}`);

    // Utilisez les options avec les en-têtes
    const options = { headers: headers };

    return this.httpClient.get(`${this.url}/dash/details`, options);  }
  
 

  }
