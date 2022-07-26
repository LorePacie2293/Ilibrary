import { Injectable } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { DialogCartBooksByUsernameComponent } from '../Dialog/dialog-cart-books-by-username/dialog-cart-books-by-username.component';
import { DialogInsertComponent } from '../Dialog/dialog-insert/dialog-insert.component';
import { DialogListOrderUsernameComponent } from '../Dialog/dialog-list-order-username/dialog-list-order-username.component';
import { DialogLoginComponent } from '../Dialog/dialog-login/dialog-login.component';
import { DialogLogoutComponent } from '../Dialog/dialog-logout/dialog-logout.component';
import { DialogMsgComponent } from '../Dialog/dialog-msg/dialog-msg.component';
import { DialogOrderDetailsComponent } from '../Dialog/dialog-order-details/dialog-order-details.component';
import { DialogRegisterComponent } from '../Dialog/dialog-register/dialog-register.component';
import { DialogUpdateComponent } from '../Dialog/dialog-update/dialog-update.component';

@Injectable({
  providedIn: 'root'
})

//Servizio aperura DIALOG
export class DialogServiceService {

  constructor(private dialog: MatDialog) { }

  openDialogLogin() {
    const dialogRef = this.dialog.open(DialogLoginComponent, {
      width:'40%',
      });
    }

  openDialogRegister() {
      const dialogRef = this.dialog.open(DialogRegisterComponent, {
        width:'40%',
      });
  }

  openDialogLogout() {
    const dialogRef = this.dialog.open(DialogLogoutComponent, {
      width:'60%',
    });

  }

  openDialogUpdate(bookId: number, mod: string){
    const dialogRef = this.dialog.open(DialogUpdateComponent, {
      width: '80%',
      height: '90%'
    });
    dialogRef.componentInstance.modality = mod;
    dialogRef.componentInstance.bookId = bookId;
  }

  //Apre la dialog per l'inserimento di un nuovo BOOK
  openDialogInsert(mod: string){
    const dialogRef = this.dialog.open(DialogUpdateComponent, {
      width: '80%',
      height: '90%'
    });
    dialogRef.componentInstance.modality = mod;
  }

  //Apre la dialog per la visualizazzione dei dettagli di 1 ordine
  openDialogOrderDetails(orderId: number, username: string){

    const dialogRef = this.dialog.open(DialogOrderDetailsComponent, {
      width: '80%',
      height: '70%'
    });
   dialogRef.componentInstance.idOrdine = orderId;
   dialogRef.componentInstance.username = username;
  }


  //Apre la dialog per la visualizazzione della lista di ordini di 1 USER
  openDialogListOrersUsername(username: string){

    const dialogRef = this.dialog.open(DialogListOrderUsernameComponent, {
      width: '80%',
      height: '70%'
    });
    dialogRef.componentInstance.username = username;

  }

  //Apre la dialog per la visualizazzione della lista di books contenuti in un cart by username
  openDialogListCartByUsername(username: string){

    const dialogRef = this.dialog.open(DialogCartBooksByUsernameComponent, {
      width: '80%',
      height: '70%'
    });
    dialogRef.componentInstance.username = username

  }

  //Apre la dialog per la visualizazzione della lista di books contenuti in un cart by username
  openDialogMessage(text: string, typeAllert: boolean){

    const dialogRef = this.dialog.open(DialogMsgComponent, {
      width: '50%',
      height: '30%'
    });
    dialogRef.componentInstance.text = text
    dialogRef.componentInstance.typeAllert = typeAllert
  }
}
