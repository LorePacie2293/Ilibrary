import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import{ArticoliModel} from 'src/app/model/articoli'


@Injectable({
  providedIn: 'root'
})
export class ArticoliService {

  articoli: ArticoliModel[]  = [
    {codArt : "014600301", descrizioneArt : 'BARILLA FARINA 1 KG', um : 'PZ', pzCart : 24, peso : 1, prezzo : 1.09, active : true, data : new Date(), imageUrl: 'assets/images/prodotti/014600301.jpg'},
    {codArt : "013500121", descrizioneArt : "BARILLA PASTA GR.500 N.70 1/2 PENNE", um : "PZ", pzCart : 30, peso : 0.5, prezzo : 1.3, active : true, data : new Date(), imageUrl: 'assets/images/prodotti/013500121.jpg'},
    {codArt : "014649001", descrizioneArt : "BARILLA PANNE RIGATE 500 GR", um : "PZ", pzCart : 12, peso : 0.5, prezzo : 0.89, active : true, data : new Date(), imageUrl: 'assets/images/prodotti/014649001.jpg'},
    {codArt : "007686402", descrizioneArt : "FINDUS FIOR DI NASELLO 300 GR", um : "PZ", pzCart : 8, peso : 0.3, prezzo : 6.46, active : true, data : new Date(), imageUrl: 'assets/images/prodotti/007686402.jpg'},
    {codArt : "057549001", descrizioneArt : "FINDUS CROCCOLE 400 GR", um : "PZ", pzCart : 12, peso : 0.4, prezzo : 5.97, active : true, data : new Date(), imageUrl: 'assets/images/prodotti/057549001.jpg'},
  ]



  constructor() { }

  //Restiruisce una lista di articoli
  getArticoli = (): ArticoliModel[] =>  this.articoli;

  //Restituisce un ARTICOLO ricercato per BARCODE
  getArticoloByBarcode = (codArt: string): ArticoliModel => {

    //Indice articolo corrispondente a l BARCODE
    const index = this.articoli.findIndex(articoli => articoli.codArt === codArt);
    return this.articoli[index];
  }


}
