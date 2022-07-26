import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  imageReturn: any;
  base64Data: any;
  retrieveResonse: any;

  constructor(private httpClient: HttpClient) { }

  //Gets called when the user clicks on retieve image button to get the image from back end
  getImage(imgName: string){
    console.log(imgName)
    //Make a call to Sprinf Boot to get the Image Bytes.
    return this.httpClient.get('http://localhost:8080/api/images/get/' + imgName)
  }

}
