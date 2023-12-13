import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-succes-dialog',
  templateUrl: './succes-dialog.component.html',
  styleUrl: './succes-dialog.component.css'
})
export class SuccesDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<SuccesDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }
}
