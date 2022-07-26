import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClientiModel } from 'src/app/model/clienti';

@Injectable({
  providedIn: 'root'
})
export class ClientiService {

  constructor(private httpClient: HttpClient) { }

  //ottiene la lista COMPLETA degli ORDINI
  getClientiAll = () => {
    return this.httpClient.get<ClientiModel[]>('http://localhost:8080/api/clienti/cerca/byAll')
  }
}
