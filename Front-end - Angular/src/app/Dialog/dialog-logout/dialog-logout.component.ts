import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { AuthenticationService } from '../../Service/authentication.service';

@Component({
  selector: 'app-dialog-logout',
  templateUrl: './dialog-logout.component.html',
  styleUrls: ['./dialog-logout.component.scss']
})
export class DialogLogoutComponent implements OnInit {

  stateLogout: boolean = false
  constructor(public basicAuth: AuthenticationJwtService, private router: Router) { }


  ngOnInit(): void {
  }

  logoutButton(){
    this.stateLogout = true
    this.router.navigate([""])
    this.basicAuth.removeAuthentication();
  }

  refreshPage(){
    window.location.reload()
  }

}
