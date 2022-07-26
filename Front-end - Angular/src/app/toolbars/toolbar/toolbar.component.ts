import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AuthenticationService } from 'src/app/Service/authentication.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';
import { OrdiniService } from 'src/app/Service/DataService/ordini.service';
import { AuthenticationJwtService } from 'src/app/Service/authenticationJwtService';
import { JwtRolesServiceService } from 'src/app/Service/jwt-roles-service.service';
import { Roles } from 'src/app/model/roles';
import { CartsService } from 'src/app/Service/DataService/carts.service';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { booksModel } from 'src/app/model/books';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(public dialogService: DialogServiceService, public authService: AuthenticationJwtService, private ordiniService: OrdiniService,
              private jwtRoles: JwtRolesServiceService, private booksService: BooksService, private cartService: CartsService) { }



  contCart: number = 0
  booksList$: booksModel[] = []

  //Verifica se l'utente ha ruolo ADMIN
  isAdmin: boolean = false
  isUser: boolean = false

  //Filtro di ricerca
  setFilter: string | null = '';

  //Emette alla pagina PADRE che è stata effettuata una ricerca
  @Output()
  search = new EventEmitter();

  //Inposta il TASTO DI VIEW dei book
  @Input()
  bottonViewBook: string = "bookTable"

  ngOnInit(): void {

    this.booksService.getBooksByCart(this.authService.logenUser()).subscribe({
      next: (response) => {

        this.booksList$ = response
        this.contCart = this.booksList$.length
      }
    })

    //Risposta automanitca quando vengono aggiunti o rimossi book dal carrello
    this.cartService.RefreshRequired.subscribe(response =>{
      this.booksService.getBooksByCart(this.authService.logenUser()).subscribe({
        next: (response) => {

          this.booksList$ = response
          this.contCart = this.booksList$.length
        }
      })
    })

    //Verifica che il ruolo dell'utente loggato sia ADMIN
    this.isAdmin = (this.jwtRoles.getRole().indexOf(Roles.admin) > -1) ? true : false;
    this.isUser = (this.jwtRoles.getRole().indexOf(Roles.user) > -1) ? true : false;

    console.log("- Component: ToolbarComponent creato ---------------------------------------------------------")
  }

  emitSearch(){

    this.search.emit(this.setFilter)
  }
}
