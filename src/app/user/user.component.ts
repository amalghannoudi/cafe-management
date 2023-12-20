import { Component } from '@angular/core';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.css'
})
export class UserComponent {
  sidebarShow = true;
  toggleSidebar() {
    this.sidebarShow = !this.sidebarShow;
  }
}
