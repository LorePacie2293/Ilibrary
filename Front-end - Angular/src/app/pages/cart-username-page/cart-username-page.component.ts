import { Component, OnInit } from '@angular/core';
import { booksModel } from 'src/app/model/books';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { CartsService } from 'src/app/Service/DataService/carts.service';
import { ImageService } from 'src/app/Service/DataService/image.service';
import { OrdiniService } from 'src/app/Service/DataService/ordini.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';

@Component({
  selector: 'app-cart-username-page',
  templateUrl: './cart-username-page.component.html',
  styleUrls: ['./cart-username-page.component.scss']
})
export class CartUsernamePageComponent implements OnInit {

  constructor(private bookService: BooksService, private imageService: ImageService, private authService: AuthenticationJwtService, private cartService: CartsService,
              private ordiniService: OrdiniService, private dialogService: DialogServiceService) { }

  imageReturn: any[] = [];
  contImg: number = 0

  base64Data: any;
  retrieveResonse: any;

  username: string | null= ""
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  books$: booksModel[] = [];                                  //Books observable
  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  errorMsg: string = "Nessun book presente nel cart"
  showError: boolean = false
  ngOnInit(): void {


    this.bookService.getBooksByCart(this.authService.logenUser()).subscribe({
      next: (Response) => {
        this.books$ = Response;

        //Carica le IMG per ogni book inserito nell'ordine
        this.books$.forEach(book => {

            this.imageService.getImage(book.imgUrl).subscribe(res => {
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.imageReturn[book.bookId] = 'data:image/jpeg;base64,' + this.base64Data;
          })

        })
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

  //Elimina un book dal CART
  removeBookToCart(bookTitle: string){

  this.cartService.removeBooksInTheCart(this.authService.logenUser(), bookTitle).subscribe(
      response => {

         this.bookService.getBooksByCart(this.authService.logenUser()).subscribe({
          next: (Response) => {
            this.books$ = Response;

            //Carica le IMG per ogni book inserito nell'ordine
            this.books$.forEach(book => {

                this.imageService.getImage(book.imgUrl).subscribe(res => {
                this.retrieveResonse = res;
                this.base64Data = this.retrieveResonse.picByte;
                this.imageReturn[book.bookId] = 'data:image/jpeg;base64,' + this.base64Data;
              })

            })
            this.dialogService.openDialogMessage("Book rimosso dal carrello", true)

          },

          error: (Response) => {

            this.dialogService.openDialogMessage("Impossibile rimuovere il book dal carrello", true)
            window.location.reload()
            this.showError = true
          }
        });

      }
    )}

    //Inserisce un nuovo ordine prendendo i books dal cart
    insertOrdine(){

      this.username = this.authService.logenUser()
      this.ordiniService.postOrdineByUsername(this.username).subscribe({
        next: (response) => {
          this.dialogService.openDialogMessage("Ordine inserito con successo", true)
        },
        error:(response) => {
          this.dialogService.openDialogMessage("Nessun book inserito nel carrello", false)
        }
      })
    }
}
