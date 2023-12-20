import { Component } from '@angular/core';

@Component({
  selector: 'app-facture',
  templateUrl: './facture.component.html',
  styleUrl: './facture.component.css'
})
export class FactureComponent {
  sidebarShow = true;
  toggleSidebar() {
    this.sidebarShow = !this.sidebarShow;
  }
}
