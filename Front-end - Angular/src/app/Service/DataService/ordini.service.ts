import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { errorMsgModel } from 'src/app/model/errorMsg';
import{ordiniModel} from 'src/app/model/ordini'

@Injectable({
  providedIn: 'root'
})
export class OrdiniService {

  constructor(private httpClient: HttpClient) { }

  //ottiene la lista COMPLETA degli ORDINI
  getOrdiniAll = () => {
    return this.httpClient.get<ordiniModel[]>('http://localhost:8080/api/ordini/get/byAll')
  }

  //Ottiene tutti gli ordini di 1 user
  getOrdiniByUsername = (username: string | null) => {
    console.log("eseguito")
    return this.httpClient.get<ordiniModel[]>('http://localhost:8080/api/ordini/get/byUsername/' + username)
  }

  //ottiene la lista degli ordini specificando il prezzo
  getOrdiniByPrice = (ordinePrice: number) => {
    return this.httpClient.get<ordiniModel[]>('http://localhost:8080/api/ordini/cerca/byPrice/99.43')
  }

  //Inserisce un nuovo ordine per l'username specificato
  postOrdineByUsername = (username: string | null) => {
    return this.httpClient.get<ordiniModel>('http://localhost:8080/api/ordini/post/Users/byUsername/' + username)
  }

  //Elimina un'ordine by idOrdine
  deleteOrdineByIdOrdine = (idOrdine: number) => {
    return this.httpClient.delete<errorMsgModel>('http://localhost:8080/api/ordini/delete/byIdOrdine/' + idOrdine)
  }
}
