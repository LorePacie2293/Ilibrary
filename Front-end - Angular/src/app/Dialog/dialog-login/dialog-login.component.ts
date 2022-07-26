import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { AuthenticationService } from '../../Service/authentication.service';
import { DialogServiceService } from '../../Service/dialog-service.service';

@Component({
  selector: 'app-dialog-login',
  templateUrl: './dialog-login.component.html',
  styleUrls: ['./dialog-login.component.scss']
})
export class DialogLoginComponent implements OnInit {


  userName: string = ""
  password: string = ""
  errorMsg: string = "Errore autenticazione: credenziali errate"
  successMsg: string = "Login effettuato con successo"

  showErrorCredential = false;
  showLoginConfirm = false;

  constructor(public basicAuth: AuthenticationJwtService, public dialogService: DialogServiceService) { }

  ngOnInit(): void {
  }

  authButton(): void{

   this.basicAuth.authenticationService(this.userName, this.password).subscribe({
    next: (response) => {

      this.showLoginConfirm = true
      this.showErrorCredential = false
    },

    error: (response) => {

      this.showLoginConfirm = false
      this.showErrorCredential = true
    }

   })
  }

  refreshPage(){
    window.location.reload()
  }
}
