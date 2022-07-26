import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { map, Observable, of} from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';
import { booksModel } from 'src/app/model/books';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';
import { JwtRolesServiceService } from 'src/app/Service/jwt-roles-service.service';
import { Roles } from 'src/app/model/roles';
import { searchEnum } from 'src/app/model/serachType';
import { CartsService } from 'src/app/Service/DataService/carts.service';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { ImageService } from 'src/app/Service/DataService/image.service';

@Component({
  selector: 'app-books-page',
  templateUrl: './books-page.component.html',
  styleUrls: ['./books-page.component.scss']
})
export class BooksPageComponent implements OnInit {

  constructor(private booksService: BooksService, private route: ActivatedRoute, private dialogservice: DialogServiceService, private jwtRoles: JwtRolesServiceService,
              private cartService: CartsService, private authService: AuthenticationJwtService, private imageService: ImageService ) { }


  contCart$: any
  imageReturn: any[] = [];
  contImg: number = 0

  base64Data: any;
  retrieveResonse: any;

  setButtonViewToolbar = "bookGrid"
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  filterType: number | null= searchEnum.srcById;              //Imposta il tipo di ricerca

  books$: booksModel[] = [];                                  //Books observable
  page: number = 1;                                           //page INDEX
  numRow: number = 9;                                         //Numero di righe della table

  msgError:string = '';
  showError: boolean = false;

  isAdmin:boolean = false;                                    //Verifica che il ruolo sia ADMIN
  modModify: string = ""

  ngOnInit(): void {

    console.log("- Component: BooksPageComponent caricato ---------------------------------------------------------")



    //Verifica che il ruolo dell'utente loggato sia ADMIN
    this.isAdmin = (this.jwtRoles.getRole().indexOf(Roles.admin) > -1) ? true : false;
    console.log((this.isAdmin) ? "-  L'utente è ADMIN" : "-  L'utente NON è ADMIN")

    //Ottiene la lista COMPLETA dei books
    this.booksService.getBooksAll().subscribe({
      next: this.handleResponse.bind(this),
      error: this.handleError.bind(this)
    })
  }

  //Ottiene ALL BOOKS se SENZA FILTER
  //Altrimento ottiene i books filtrati
  getBooks(filter: string | null){

    //Se il filtro NON e stato inserito ottieni TUTTI i libri
    if(this.setFilter == null || this.setFilter == ''){
      this.booksService.getBooksAll().subscribe({
        next: this.handleResponse.bind(this),
        error: this.handleError.bind(this)
      })
    }

    else{
        //Ricerca per TITLE
        if(this.filterType == searchEnum.srcByTitle){
          console.log("-  Ricerca: " + this.setFilter + " by TITLE ----------")
          this.booksService.getBooksByTitle(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per YEAR RELEASED
        else if(this.filterType == searchEnum.srcByyear){
          console.log("-  Ricerca: " + this.setFilter + " by YEAR RELEASED ----------")
          this.booksService.getBooksByYear(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per ID
        else if(this.filterType == searchEnum.srcById){
          console.log("-  Ricerca: " + this.setFilter + " by ID ----------")
          this.booksService.getBooksById(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per author first name
        else if(this.filterType == searchEnum.srcByAuthorFirstNamme){
          console.log("-  Ricerca: " + this.setFilter + " by Author first name ----------")
          this.booksService.getBooksByAuthorFirstName(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per author last name
        else if(this.filterType == searchEnum.srcByAuthorLastName){
          console.log("-  Ricerca: " + this.setFilter + " by Author last name ----------")
          this.booksService.getBooksByAuthorLastName(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per stock quantity
        else if(this.filterType == searchEnum.srcByStockQuantity){
          console.log("-  Ricerca: " + this.setFilter + " by stock quantity ----------")
          this.booksService.getBooksByStockQuantity(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }

        //Ricerca per pages
        else if(this.filterType == searchEnum.srcByPages){
          console.log("-  Ricerca: " + this.setFilter + " by pages ----------")
          this.booksService.getBooksByPages(this.setFilter).subscribe({
            next: this.handleResponse.bind(this),
            error: this.handleError.bind(this)
          })
        }
      }
    }

  //Assegna la LISTA DI BOOK ottenuta dal server, e la assegna alla list books$
  handleResponse(response: any){

    //Inizializza books list
    this.books$ = []

    //Nascondi msg error se risposta positiva
    this.showError = false;

    //Se non è stato inserito NESSUN filtro richiedi all book
    if(this.setFilter == null || this.setFilter == ''){
      console.log("-* BooksPageComponent - GET booksByAll - OK")
      this.books$ = response;
    }else{

    //Gestisce le RESPONSE che restituisco 1 solo risultato
    if(this.filterType == 0){

      console.log("-* BooksPageComponent - ricerca OK")

      //Crea ed inizializza un nuovo array con i valori di books$ attuali + il nuovo elemento
        let tempArray: booksModel[] = [...this.books$, response];
        this.books$ = tempArray;
        console.log(this.books$)
     }
     else{
      console.log("-* BooksPageComponent - ricerca OK")
        this.books$ = response;
     }
    }

    //Carica le IMG per ogni book inserito nell'ordine
    this.books$.forEach(book => {

      this.imageService.getImage(book.imgUrl).subscribe(res => {
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.imageReturn[book.bookId] = 'data:image/jpeg;base64,' + this.base64Data;
      })
    })
  }

  //In caso di errore, tenta la ricerca con il filtro attuale, se fallisce ancora,
  //tenta con i filtri successivi
  //in caso di esito negativo, restituisce il codice di errore.
  handleError(error: any) {


    //Falliment con src ID
    if(this.setFilter && this.filterType === searchEnum.srcById){
      console.log("-  Ricerca per id FALLITA")

      //tenta con srcByTitle
      this.filterType = searchEnum.srcByTitle;
      this.getBooks(this.setFilter)
    }

    //Fallimento con srcByTitle
    else if(this.setFilter && this.filterType === searchEnum.srcByTitle){
      console.log("-  Ricerca per TITLE FALLITA")

       //tenta con srcByyear
       this.filterType = searchEnum.srcByyear;
       this.getBooks(this.setFilter)
    }

    //Fallimento con srcByyear
    else if(this.setFilter && this.filterType === searchEnum.srcByyear){
      console.log("-  Ricerca per YEAR FALLITA")

       //tenta con srcByAuthorFirstNamme
       this.filterType = searchEnum.srcByAuthorFirstNamme;
       this.getBooks(this.setFilter)
    }

    //Fallimento con srcByAuthorFirstNamme
    else if(this.setFilter && this.filterType === searchEnum.srcByAuthorFirstNamme){
      console.log("-  Ricerca per AuthorFirstNamme FALLITA")

       //tenta con srcByAuthorLastNamme
       this.filterType = searchEnum.srcByAuthorLastName;
       this.getBooks(this.setFilter)
    }

    //Fallimento con srcByAuthorFirstNamme
    else if(this.setFilter && this.filterType === searchEnum.srcByAuthorLastName){
      console.log("-  Ricerca per AuthorLastNamme FALLITA")

       //tenta con srcByAuthorLastNamme
       this.filterType = searchEnum.srcByStockQuantity;
       this.getBooks(this.setFilter)
    }

     //Fallimento con srcByStockQuantity
     else if(this.setFilter && this.filterType === searchEnum.srcByStockQuantity){
      console.log("-  Ricerca per StockQuantity FALLITA")

       //tenta con srcByAuthorLastNamme
       this.filterType = searchEnum.srcByPages;
       this.getBooks(this.setFilter)
    }
    else{

    console.log("-  Ricerca per pages FALLITA")

    //Controlla se dopo l'ultima ricerca, non sia stato trovato alcun risultato, in tal caso visualizza il msg
    if(this.msgError != ''){
      this.showError = true;
    }

    this.msgError = "Impossibile trovare una corrispondenza";
    console.log("-  " + this.msgError)
    //Ripristino search type
    this.filterType = 0
   }
  }

  //Elimina un book dal DB
  remove(CodBook: number){
    this.booksService.delBookByCodArt(CodBook).subscribe({

        next: (response) => {

           //Filtra i tutti i book ricevuti per lasciare FUORI quello eliminato
          this.books$ = this.books$.filter(book => book.bookId !== CodBook)

          console.log(this.books$)
          this.refreshPage()
          this.dialogservice.openDialogMessage("Book eliminato con successo", true)

        },

        error: (response) => {
          this.dialogservice.openDialogMessage("Impossibile eliminare il book", false)

        }
      }
    )}

  update(codBook: number, modality: string){
    console.log("modifcia")
    this.dialogservice.openDialogUpdate(codBook, modality)
  }

  //Ottiene la lista di BOOKS FILTRATI dopo l'evento JEY UP
  refreshPage(){
    console.log("-------------------------------------------------------------------------------------------")
    console.log("-  BooksPageComponent - Inizio ricerca per: " + this.setFilter)
    this.getBooks(this.setFilter)
  }

  //Inizia la ricerca per il filtro inserito
  startSearch(filter: string | null){

    console.log("Filter " + filter)
    this.setFilter = filter;
    console.log("-------------------------------------------------------------------------------------------")
    console.log("-  BooksPageComponent - Inizio ricerca per: " + this.setFilter)
    this.getBooks(this.setFilter)

  }

  insertBooksInTheCart(bookTitle: string){

    if(this.authService.userAuthStat()){

      this.cartService.insertBooksInTheCart(this.authService.logenUser(), bookTitle).subscribe({
        next: (Response) => {
          this.dialogservice.openDialogMessage("Book aggiunto al carrello", true)
        },

        error: (Response) => {
          this.dialogservice.openDialogMessage("Impossibile aggiungere il book al carrello", false)
        }
      });
    }
    else{
      this.dialogservice.openDialogLogin()
    }
  }
}

