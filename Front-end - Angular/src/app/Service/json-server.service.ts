import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { booksModel } from '../model/books';

@Injectable({
  providedIn: 'root'
})
export class JsonServerService {

  constructor(private httpClient: HttpClient) { }

  addBooks(book: booksModel){
    return this.httpClient.post("http://localhost:8080/api/books", book)
  }

  getBooks(){
    return this.httpClient.get("http://localhost:3000/books/");
  }
}
