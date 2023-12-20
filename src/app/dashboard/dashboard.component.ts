import { Component } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { DashboardService } from '../service/dashboard.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  sidebarShow = true;
  responseMsg: any;
  data: any;

  constructor(
    private dashboardService: DashboardService,
    private ngxService: NgxUiLoaderService
  ) {
    this.ngxService.start();
    this.dashboardData();
  }

  dashboardData() {
    this.dashboardService.getDetails().subscribe(
      (response) => {
        this.ngxService.stop();
        console.log(response);
        this.data = response;
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMsg = error.error?.message;
        } else {
          this.responseMsg = GlobalConstants.genericError;
        }
      }
    );
  }
  toggleSidebar() {
    this.sidebarShow = !this.sidebarShow;
  }
}
