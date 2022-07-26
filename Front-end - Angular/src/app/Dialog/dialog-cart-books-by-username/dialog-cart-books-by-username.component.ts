import { Component, OnInit } from '@angular/core';
import { booksModel } from 'src/app/model/books';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { CartsService } from 'src/app/Service/DataService/carts.service';
import { ImageService } from 'src/app/Service/DataService/image.service';

@Component({
  selector: 'app-dialog-cart-books-by-username',
  templateUrl: './dialog-cart-books-by-username.component.html',
  styleUrls: ['./dialog-cart-books-by-username.component.scss']
})
export class DialogCartBooksByUsernameComponent implements OnInit {

  constructor(private bookService: BooksService, private imageService: ImageService, public authService: AuthenticationJwtService, private cartService: CartsService) { }


  username: string = ""
  imageReturn: any[] = [];
  contImg: number = 0

  base64Data: any;
  retrieveResonse: any;

  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  books$: booksModel[] = [];                                  //Books observable
  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  errorMsg: string = "Nessun book presente nel cart"
  showError: boolean = false
  message: string = ""

  ngOnInit(): void {

    this.bookService.getBooksByCart(this.username).subscribe({
      next: (Response) => {
        this.books$ = Response;

        //Carica le IMG per ogni book inserito nell'ordine
        this.books$.forEach(book => {

            this.imageService.getImage(book.imgUrl).subscribe(res => {
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.imageReturn[book.bookId] = 'data:image/jpeg;base64,' + this.base64Data;
          })
          this.message = "Carrello di user: " + this.username
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
          },

          error: (Response) => {

            window.location.reload()
            this.showError = true
          }
        });

      }
    )}

}
