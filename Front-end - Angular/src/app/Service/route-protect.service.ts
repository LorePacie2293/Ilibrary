import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { AuthenticationJwtService } from './authenticationJwtService';
import { DialogServiceService } from './dialog-service.service';
import { JwtRolesServiceService } from './jwt-roles-service.service';

@Injectable({
  providedIn: 'root'
})

//Disattiva determinate rotte in caso di utente non autenticato
export class RouteProtectService implements CanActivate {

  roleToken: string[] = new Array();

  constructor(private authJwtService: AuthenticationJwtService, private router: Router, private roleService: JwtRolesServiceService, private dialogService: DialogServiceService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {


    //Verifica che l'utente sia LOGGATO
    if(!this.authJwtService.userAuthStat()){
      this.dialogService.openDialogLogin()
      return false;
    }
    else{

      //Ottiene RUOLO memorizzato nel TOKEN in SESSION STORAGE
      this.roleToken = this.roleService.getRole();

      //Ottiene i ruoli assegnati alla ROUTE
      let role: string[] = new Array();
      role = route.data['roles']

      //Se non ce protezione di RUOLO assegnata alla ROUTE
      if(role == null || role.length == 0){
        console.log("Route libera - accesso consentito")
        return true;
      }

      //Altrimenti scorri tutti i ruoli dell route, ed confronto con ruolo del token
      else if(this.roleToken.some(roleTok => role.includes(roleTok))){
        console.log("Ruolo accettato")
        return true;
      }
      else{
        //this.router.navigate(['forbiden']);
        console.log("Ruolo insufficiente")
        return false;
      }

    }
  }
}
