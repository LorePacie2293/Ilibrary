import { Component, OnInit } from '@angular/core';
import { ordiniModel } from 'src/app/model/ordini';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { OrdiniService } from 'src/app/Service/DataService/ordini.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';

@Component({
  selector: 'app-ordini',
  templateUrl: './ordini.component.html',
  styleUrls: ['./ordini.component.scss']
})

export class OrdiniComponent implements OnInit {

  constructor(private authService: AuthenticationJwtService, private ordiniService: OrdiniService, private dialogService: DialogServiceService) { }

  errorMsg: string = "Nessun ordine disponibile"
  showError: boolean = false
  username: string | null= ""
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  orders$: ordiniModel[] = []
  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  ngOnInit(): void {

    this.ordiniService.getOrdiniAll().subscribe({
      next: (Response: ordiniModel[]) => {
        this.orders$ = Response;
        this.showError = false
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

  //Elimina un'ordine specificandone ID
  removeOrderById(orderId: number){

    this.ordiniService.deleteOrdineByIdOrdine(orderId).subscribe({
      next: (response) => {
        this.ordiniService.getOrdiniAll().subscribe({
          next: (Response: ordiniModel[]) => {
            this.orders$ = Response;
            this.showError = false
            this.dialogService.openDialogMessage("Ordine eliminato con successo", true)

          },

          error: (Response: ordiniModel[]) => {
            this.showError = true
            window.location.reload()
            this.dialogService.openDialogMessage("Impossibile eliminare l'ordine", false)
          }
        });
      }
    })

  }

}
