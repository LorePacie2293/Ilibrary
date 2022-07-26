import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartsService {

  constructor(private httpClient: HttpClient) { }
  server: string = "localhost";
  port: string = "8080";

  //Imposta auto refresh quando vengono aggiunti o rimossi book dal carrello
  private _refreshRequired = new Subject<void>();

  get RefreshRequired(){
    return this._refreshRequired
  }

  //Inserimento nuovo books in un CART
  insertBooksInTheCart = (username: string | null, bookTitle: string) => {
    console.log("-  POST /api/carts/post/{username}/booksByTitle/{bookTitle}")
    return this.httpClient.get(`http://${this.server}:${this.port}/api/carts/get/${username}/booksByTitle/${bookTitle}`).pipe(
      tap(() => {
        this.RefreshRequired.next()
      })
    )
  }

   //Rimuove un book da un CART
   removeBooksInTheCart = (username: string | null, bookTitle: string) => {
    console.log("-  POST /api/carts/delete/{username}/booksByTitle/{bookTitle}")
    return this.httpClient.delete(`http://${this.server}:${this.port}/api/carts/delete/${username}/booksByTitle/${bookTitle}`).pipe(
      tap(() => {
        this.RefreshRequired.next()
      })
    )
  }
}
