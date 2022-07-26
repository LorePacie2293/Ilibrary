import { HttpClient } from '@angular/common/http';
import { ThisReceiver } from '@angular/compiler';
import { Injectable } from '@angular/core';
import { map, Subject } from 'rxjs';
import { booksModel } from 'src/app/model/books';
import { errorMsgModel } from 'src/app/model/errorMsg';

@Injectable({
  providedIn: 'root'
})

export class BooksService {

  server: string = "localhost";
  port: string = "8080";

  constructor(private httpClient: HttpClient) { }

  

  //ottiene la lista COMPLETA dei BOOK
  getBooksAll = () => {
    console.log("-  GET /api/books/get/byAll")
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byAll`)

  }

  //Ottiene una lista di Books by Id
  getBooksById = (bookId: number | string) =>{
    console.log("-  GET /api/books/get/byId/" + bookId)
    return this.httpClient.get<booksModel>('http://localhost:8080/api/books/get/byId/' + bookId)
  }

  //Ottiene una lista di Books by TITLE
  getBooksByTitle = (bookTitle: string) =>{
    console.log("-  GET /api/books/get/byTitle/" + bookTitle)
    return this.httpClient.get<booksModel[]>('http://localhost:8080/api/books/get/byTitle/' + bookTitle)
  }

  //Ottiene una lista di Books by author first name
  getBooksByAuthorFirstName = (authorFname: string) =>{
    console.log("-  GET /api/books/get/byAuthFname/" + authorFname)
    return this.httpClient.get<booksModel[]>('http://localhost:8080/api/books/get/byAuthFname/' + authorFname)
  }

  //Ottiene una lista di Books by author last name
  getBooksByAuthorLastName = (authorLname: string) =>{
    console.log("-  GET /api/books/get/byAuthLname/" + authorLname)
    return this.httpClient.get<booksModel[]>('http://localhost:8080/api/books/get/byAuthLname/' + authorLname)
  }

  //Ottiene una lista di Books by Year
  getBooksByYear = (bookYear: string) =>{
    console.log("-  GET /api/books/get/byYear/" + bookYear)
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byYear/${bookYear}`)
  }

  //Ottiene una lista di Books by Year
  getBooksByStockQuantity = (quantity: string) =>{
    console.log("-  GET /api/books/get/byStockQuantity/" + quantity)
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byStockQuantity/${quantity}`)
  }

  //Ottiene una lista di Books by Year
  getBooksByPages = (pages: string) =>{
    console.log("-  GET /api/books/get/byPages/" + pages)
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byPages/${pages}`)
  }

  //Ottiene una lista di Books byID ORDINE
  getBooksByOrdine = (idOrdine: number) =>{
    console.log("-  GET /api/books/get/byOrdine/" + idOrdine)
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byOrdine/${idOrdine}`)
  }

  //Ottiene una lista di Books byID CART = USER
  getBooksByCart = (username: string | null) =>{
    console.log("-  GET /api/books/get/byCart/" + username)
    return this.httpClient.get<booksModel[]>(`http://${this.server}:${this.port}/api/books/get/byCart/${username}`)
  }

  //Inserimento nuovo books
  insertBooks = (book: booksModel) => {
    console.log("-  POST /api/books/post/")
    return this.httpClient.post<booksModel>(`http://${this.server}:${this.port}/api/books/post`, book)
  }

  //Aggiorno il BOOK
  updateBook = (book: booksModel) =>{
    return this.httpClient.put<booksModel>(`http://${this.server}:${this.port}/api/books/put`, book)
  }
  //Eliminazioni book con ID
  delBookByCodArt = (idBook: number)=> {
    return this.httpClient.delete<errorMsgModel>('http://localhost:8080/api/books/delete/' + idBook)
  }
}


