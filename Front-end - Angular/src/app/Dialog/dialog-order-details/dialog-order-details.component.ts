import { Component, OnInit } from '@angular/core';
import { booksModel } from 'src/app/model/books';
import { ordiniModel } from 'src/app/model/ordini';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { ImageService } from 'src/app/Service/DataService/image.service';

@Component({
  selector: 'app-dialog-order-details',
  templateUrl: './dialog-order-details.component.html',
  styleUrls: ['./dialog-order-details.component.scss']
})
export class DialogOrderDetailsComponent implements OnInit {

  constructor(private bookService: BooksService, private imageService: ImageService, private authService: AuthenticationJwtService) { }


  imageReturn: any[] = [];
  contImg: number = 0

  base64Data: any;
  retrieveResonse: any;

  username: string = ""
  idOrdine: number = 0
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  books$: booksModel[] = [];                                  //Books observable
  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  message: string = ""
  showError: boolean = false
  errorMsg: string = "Nessun book presente nel carrello di " + this.authService.logenUser()

  ngOnInit(): void {

    this.message = "OrdineId: " + this.idOrdine + " - di  user: " + this.username
    this.bookService.getBooksByOrdine(this.idOrdine).subscribe({
      next: (Response) => {
        this.books$ = Response;

        //Carica le IMG per ogni book inserito nell'ordine
        this.books$.forEach(book => {

            this.imageService.getImage(book.imgUrl).subscribe(res => {
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.imageReturn[book.bookId] = 'data:image/jpeg;base64,' + this.base64Data;
            this.contImg += 1
          })
        })
        this.showError = false
      },

      error: (Response) => {
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

}
