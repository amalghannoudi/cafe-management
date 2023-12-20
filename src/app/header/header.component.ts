import { Component, EventEmitter, Output } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ConfirmationComponent } from '../dialog/succes-dialog/confirmation/confirmation.component';
import { ChangePassComponent } from '../dialog/succes-dialog/change-pass/change-pass.component';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  @Output() sidebarToggle = new EventEmitter<void>();
role :any ; 
isMenuOpen = false;

constructor (private router : Router, private dialog:MatDialog){

}

  toggleSidebar() {
    this.sidebarToggle.emit();
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  logOut(){
    const dialogConfig = new MatDialogConfig() ; 
    dialogConfig.data={
      message :'LogOut',
      confirmation : true 
    }; 
    const dialogRef =this.dialog.open(ConfirmationComponent,dialogConfig);
  const sub= dialogRef.componentInstance.onEmitStatusChange.subscribe((response)=>{
    dialogRef.close(); 
    localStorage.clear(); 
    this.router.navigate(['/login']) ; 
  })
  this.isMenuOpen = false; // Close the menu after clicking

}
  changePassword(){
    const dialogConfig = new MatDialogConfig() ; 
     dialogConfig.width="550px"; 
     this.dialog.open(ChangePassComponent,dialogConfig);
     this.isMenuOpen = false; // Close the menu after clicking

  }
}
