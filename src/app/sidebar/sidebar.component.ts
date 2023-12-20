import { Component, Input } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { MenuItems } from '../shared/MenuItems';
import {jwtDecode} from "jwt-decode"
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  @Input() linkActive: string = '';
  @Input() sidebarShow: boolean = false;
 
  currentRoute: string='';
  userRole:any ; 
  token :any= localStorage.getItem('token') ; 
  tokenPayload:any ; 


  constructor(private router: Router,public menuItems :MenuItems) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = event.url;
      }
    });
    this.tokenPayload=jwtDecode(this.token); 
    this.userRole = this.tokenPayload?.role ; 
  }
  toggleSidebar() {
    this.sidebarShow = !this.sidebarShow;
  }
}
