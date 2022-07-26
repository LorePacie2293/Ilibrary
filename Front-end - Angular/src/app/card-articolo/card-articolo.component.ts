import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ArticoliModel } from '../model/articoli';
import { booksModel } from '../model/books';
import { Roles } from '../model/roles';
import { AuthenticationJwtService } from '../Service/authenticationJwtService';
import { CartsService } from '../Service/DataService/carts.service';
import { ImageService } from '../Service/DataService/image.service';
import { DialogServiceService } from '../Service/dialog-service.service';
import { JwtRolesServiceService } from '../Service/jwt-roles-service.service';

@Component({
  selector: 'app-card-articolo',
  templateUrl: './card-articolo.component.html',
  styleUrls: ['./card-articolo.component.scss']
})
export class CardArticoloComponent implements OnInit {

  constructor(private imageService: ImageService, private jwtRoles: JwtRolesServiceService, private authService: AuthenticationJwtService, private cartservice: CartsService,
              private dialogService: DialogServiceService) { }

  isAdmin:boolean = false;                                    //Verifica che il ruolo sia ADMIN
  isUser: boolean = false;
  
  imageReturn: any;
  base64Data: any;
  retrieveResonse: any;

  @Input()
  book: booksModel = {
    bookId: 0,
    bookTitle: "",
    authorFname: "",
    authorLname: "",
    bookState: false,
    releasedYear: 0,
    stockQuantity: 0,
    pages: 0,
    price: 0.0,
    booksCategory: [],
    imgUrl: ""
  }

  @Output()
  del = new EventEmitter();

  @Output()
  edit = new EventEmitter();

  ngOnInit(): void {

    //Verifica che il ruolo dell'utente loggato sia ADMIN
    this.isAdmin = (this.jwtRoles.getRole().indexOf(Roles.admin) > -1) ? true : false;
    this.isUser = (this.jwtRoles.getRole().indexOf(Roles.user) > -1) ? true : false;

    this.imageService.getImage(this.book.imgUrl).subscribe(res => {
      this.retrieveResonse = res;
      this.base64Data = this.retrieveResonse.picByte;
      this.imageReturn = 'data:image/jpeg;base64,' + this.base64Data;
    })

  }

  editBook = () => this.edit.emit(this.book.bookId);
  deleteBook = () => this.del.emit(this.book.bookId);


  insertBooksInTheCart(bookTitle: string){

    if(this.authService.userAuthStat()){
      this.cartservice.insertBooksInTheCart(this.authService.logenUser(), bookTitle).subscribe({
        next: (Response) => {
          this.dialogService.openDialogMessage("Book aggiunto al carrello", true)
        },

        error: (Response) => {
          this.dialogService.openDialogMessage("Impossibile aggiungere il book al carrello", false)
        }
      });
    }
    else{
      this.dialogService.openDialogLogin()
    }
  }
}
