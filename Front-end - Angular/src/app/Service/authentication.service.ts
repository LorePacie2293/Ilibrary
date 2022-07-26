import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';
import { errorMsgModel } from '../model/errorMsg';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) { }
  server: string = "localhost";
  port: string = "8080";

  logenUser = (): string | null => (sessionStorage.getItem("Username") ? sessionStorage.getItem("Username") : "");

  userAuthStat = (): boolean => (sessionStorage.getItem("Username")) ? true : false;

  //Servizio di autenticazione BASIC
  authenticationService(userId: string, pass: string){

    let authString: string = "Basic " + window.btoa(userId + ":" + pass);
    console.log(this.logenUser())
    
    //Definizione HEADER authentication
    let headers = new HttpHeaders(
      {Authorization: authString}
    )

    return this.httpClient.get<errorMsgModel>(`http://${this.server}:${this.port}/api/books/test`, {headers}).pipe(
      map( respose => {
        sessionStorage.setItem("Username", userId)
        sessionStorage.setItem("AuthToken", authString)
        return Response;
      })
    )

  }

  removeAuthentication = () => (sessionStorage.removeItem("Username"), sessionStorage.removeItem("AuthToken"))

}
