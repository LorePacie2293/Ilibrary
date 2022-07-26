import { Component, OnInit } from '@angular/core';
import { ArticoliModel } from 'src/app/model/articoli';
import { booksModel } from 'src/app/model/books';
import { searchEnum } from 'src/app/model/serachType';
import { ArticoliService } from 'src/app/Service/DataService/articoli.service';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';


@Component({
  selector: 'app-grid-books-page',
  templateUrl: './grid-books-page.component.html',
  styleUrls: ['./grid-books-page.component.scss']
})
export class GridBooksPageComponent implements OnInit {

  constructor(private booksService: BooksService, private dialogService: DialogServiceService) { }

  setButtonViewToolbar = "bookTable"
  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  filterType: number | null= searchEnum.srcById;              //Imposta il tipo di ricerca

  msgError:string = '';
  showError: boolean = false;

  books$: booksModel[] = [];
  numColonne = 5;

  searchOption: string[] = [
    "All"
  ]

  ngOnInit(): void {

    this.booksService.getBooksAll().subscribe(response =>
    {
      this.books$ = response
    });

  }

  removeBook = (codBook: number) => {

      this.booksService.delBookByCodArt(codBook).subscribe(
        response => {

          this.booksService.getBooksAll().subscribe({
            next: (Response) => {
              this.books$ = Response;
              this.dialogService.openDialogMessage("Book eliminato con successo", true)
            },

            error: (Response) => {

              window.location.reload()
              this.showError = true
              this.dialogService.openDialogMessage("Impossibile eliminare book", false)

            }
          });

        }
    )}


  updateBook (codBook: number, modality: string){

      this.dialogService.openDialogUpdate(codBook, modality)
  }

  //Inizia la ricerca per il filtro inserito
  startSearch(filter: string | null){

    console.log("Filter " + filter)
    this.setFilter = filter;
    console.log("-------------------------------------------------------------------------------------------")
    console.log("-  BooksPageCardComponent - Inizio ricerca per: " + this.setFilter)
    this.getBooks(this.setFilter)

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


}
