import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { errorMsgModel } from '../model/errorMsg';
import { Token } from '../model/token';

@Injectable({
  providedIn: 'root'
})

//Gestisce l'autenticazione JWT
export class AuthenticationJwtService {

  constructor(private httpClient: HttpClient) { }
  server: string = "localhost";
  port: string = "8080";

  //Restituisce l'username dell'utente loggato
  logenUser = (): string | null => (sessionStorage.getItem("Username") ? sessionStorage.getItem("Username") : "");

  //Verifica che ci sia un'utente loggato
  userAuthStat = (): boolean => (sessionStorage.getItem("Username")) ? true : false;

  //Ottiene il token se presente nella SESSION STORAGE
  //Se NON e presente restituisce una stringa ""(TOKEN NON VALIDO)
  getToken = (): string=> {

    console.log("-    Ottientimento TOKEN in SESSION STORAGE")
    let AuthHeader : string = "";
    var AuthToken =  sessionStorage.getItem("AuthToken");

    if (AuthToken != null){
      AuthHeader = AuthToken;
      console.log("-    Token ottenuto: " + AuthHeader)
    }
    else
      console.log("-    Token non presente in SESSION STORAGE")

    return AuthHeader;
  }

  //Servizio di autenticazione
  //Imposta USERNAME e TOKEN nella SESSION STORAGE
  authenticationService(username: string, password: string){

    return this.httpClient.post<Token>(`${environment.authServerUri}`, {username, password}).pipe(
      map( response => {
        sessionStorage.setItem("Username", username)
        sessionStorage.setItem("AuthToken", `Bearer ${response.token}`)
        return Response;
      })
    )
  }

  //Servizio di refresh token
  //Imposta USERNAME e TOKEN nella SESSION STORAGE
  refreshTokenService(token: string){

    let headers = new HttpHeaders(
      {Authorization: token}
    )

    return this.httpClient.get<Token>(`${environment.refreshServerUri}`, {headers})
  }

  //Rimuove
  removeAuthentication = () => (sessionStorage.removeItem("Username"), sessionStorage.removeItem("AuthToken"))

}
