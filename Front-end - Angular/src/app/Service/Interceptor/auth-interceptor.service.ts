import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../authentication.service';
import { AuthenticationJwtService } from '../authenticationJwtService';

@Injectable({
  providedIn: 'root'
})

//INTERCETTORE che aggiunge alla REQUEST il token, se presente
export class AuthInterceptorService implements HttpInterceptor{

  constructor(private authJwtService: AuthenticationJwtService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    console.log("-  Intercettore 1 avviato")

    //Recupero TOKEN
    let authToken = this.authJwtService.getToken()
    let authHeader: string = "";

    //Se il token E presente nella SESSION STORAGE
    if(authToken != ""){
      authHeader = authToken



       //AGGIUNGE l'AUTHORIZATION HEADERS alla request
       if(this.authJwtService.userAuthStat()){
         req = req.clone({
        setHeaders: {Authorization : authHeader }
      });

      console.log("-    Authorization header aggiunto")
      }
   }
   
    //Restituisce la request
    return next.handle(req);
  }
}
