import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationJwtService } from './Service/authenticationJwtService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

//Ogni volta che l'applicazione viene avviata, prova ad effetuare il refresh del token
export class AppComponent {
  title = 'AngularCrudMaterial';

  constructor(private authJwtService: AuthenticationJwtService){}


  ngOnInit(): void {

    let authToken = this.authJwtService.getToken()

    this.authJwtService.refreshTokenService(authToken).subscribe({

      //Se il token è stato aggiornato, impostalo nella SESSION STORAGE
      next: (response) => {
        console.log("Refresh")
        sessionStorage.setItem("AuthToken", `Bearer ${response.token}`)

      },

      //Altrimenti rimuovi username e token dalla SESSION STORAGE
      error: (response) => {
        sessionStorage.removeItem("AuthToken")
        sessionStorage.removeItem("Username")
      }

     })

  }
}
