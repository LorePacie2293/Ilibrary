import { Component, OnInit } from '@angular/core';
import { usersModel } from 'src/app/model/users';
import { UsersService } from 'src/app/Service/DataService/users.service';
import { DialogServiceService } from 'src/app/Service/dialog-service.service';

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.scss']
})
export class UsersPageComponent implements OnInit {

  constructor(private usersService: UsersService, private dialogService: DialogServiceService) { }

  setFilter: string | null = '';                              //PAROLA CHIAVE PER RICERCA
  users$: usersModel[] = [];                                   //Users observable

  page: number = 1;                                           //page INDEX
  numRow: number = 4;                                         //Numero di righe della table

  ngOnInit(): void {
    //Ottiene la lista di USERS
    this.usersService.getUsersAll().subscribe({
      next: (Response) => {
        this.users$ = Response;

      },

      error: (Response) => {

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



  //Elimina un USER dal DB
  removeUser(username: string){
    this.usersService.delUserByUsername(username).subscribe(
      response => {

        //Filtra i tutti gli USER per lasciare FUORI quello eliminato
        this.users$ = this.users$.filter(user => user.username !== username)
        this.dialogService.openDialogMessage("User eliminato con successo", true)


      }
    )}

  displayOrderByUsername(username: string){

      this.dialogService.openDialogListOrersUsername(username)
  }

  displayCartDetails(username: string){

    this.dialogService.openDialogListCartByUsername(username);
  }
}
