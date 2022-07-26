import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { usersModel } from 'src/app/model/users';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { UsersService } from 'src/app/Service/DataService/users.service';

@Component({
  selector: 'app-dialog-register',
  templateUrl: './dialog-register.component.html',
  styleUrls: ['./dialog-register.component.scss']
})

export class DialogRegisterComponent implements OnInit {

 msgError:string = '';
 msgSuccess: string = "Inserimento user effettuato con successo"
 showSuccess: boolean = false;
 showError: boolean = false;

 bookId: number = 0;
 user$: usersModel = {
   "userId": 0,
   "userFirstName": "",
   "userLastName": "",
   "username": "",
   "userEmail": "",
   "userPassword": "",
   "userRole": "USER"
 };

 constructor(private usersService: UsersService, private httpClient: HttpClient, public authService: AuthenticationJwtService) { }

 ngOnInit(): void {
 }


 handleResponse(response: any){
   console.log(response)
   this.showSuccess = true
   this.showError = false;

 }

 handleResponseError(error: any){
   console.log(error.error.messaggio.toString())
   this.showError = true;
   this.showSuccess = false

   this.msgError = error.error.messaggio.toString();
 }




 //Inserisci nuovo BOOK POST
 registerUser(){

   this.usersService.registerUser(this.user$).subscribe(
     {
        next: this.handleResponse.bind(this),
        error: this.handleResponseError.bind(this)

      }
    )
 }

}
