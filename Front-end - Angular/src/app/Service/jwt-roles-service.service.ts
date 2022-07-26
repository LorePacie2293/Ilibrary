import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationJwtService } from './authenticationJwtService';

@Injectable({
  providedIn: 'root'
})

//Determina il RUOLO dell'utente DECODIFICANDO il token, e ricavandone il ruolo.
export class JwtRolesServiceService {

  constructor(private authJwt: AuthenticationJwtService) { }

  getRole(): string[]{

    console.log("-  Richesta ROLE utente")

    let token: string | null = null
    let roleToken: string[] = new Array();
    let items: any;

    //Ottiene token MEMORIZZATO(Login)
    token= this.authJwt.getToken()

    //Ottiene JwtHelperService per DECODIFICARE TOKEN
    const helper = new JwtHelperService();

    //DECODIFICA TOKEN
    const tokenDecoder = helper.decodeToken(token)
    items = tokenDecoder

    //Se il token è presente, ricava ROLE
    if(items != null){
      roleToken = items.authorities
    }

    return roleToken;

  }
}
