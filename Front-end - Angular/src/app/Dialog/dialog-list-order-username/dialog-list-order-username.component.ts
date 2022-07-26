import { Component, OnInit } from '@angular/core';
import { ordiniModel } from 'src/app/model/ordini';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { OrdiniService } from 'src/app/Service/DataService/ordini.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';

@Component({
  selector: 'app-dialog-list-order-username',
  templateUrl: './dialog-list-order-username.component.html',
  styleUrls: ['./dialog-list-order-username.component.scss']
})
export class DialogListOrderUsernameComponent implements OnInit {

  constructor(private authService: AuthenticationJwtService, private ordiniService: OrdiniService, private dialogService: DialogServiceService) { }



  showError: boolean = false
  username: string | null= ""
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  orders$: ordiniModel[] = []
  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  errorMsg: string = "Nessun ordine disponibile"
  message: string = ""

  ngOnInit(): void {

    this.ordiniService.getOrdiniByUsername(this.username).subscribe({
      next: (Response: ordiniModel[]) => {
        this.orders$ = Response;
        this.showError = false
        this.message = "Ordini di user: " + this.username
      },

      error: (Response: ordiniModel[]) => {
        this.showError = true
      }
    });
  }

  //Inizia la ricerca per il filtro inserito
  startSearch(filter: string | null){

    console.log("Filter " + filter)
    this.setFilter = filter;
    console.log("-------------------------------------------------------------------------------------------")
    console.log("-  BooksPageComponent - Inizio ricerca per: " + this.setFilter)


  }

  displayOrderDetails(orderId: number, username: string){

    this.dialogService.openDialogOrderDetails(orderId, username);
  }

}
