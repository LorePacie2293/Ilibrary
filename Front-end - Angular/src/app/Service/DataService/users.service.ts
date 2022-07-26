import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { errorMsgModel } from 'src/app/model/errorMsg';
import { usersModel } from 'src/app/model/users';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  server: string = "localhost";
  port: string = "8080";

  constructor(private httpClient: HttpClient) { }

   //ottiene la lista COMPLETA degli USER
   getUsersAll = () => {
    console.log("-  GET /api/users/get/byAll")
    return this.httpClient.get<usersModel[]>(`http://${this.server}:${this.port}/api/users/get/byAll`)

  }

  //Inserimento nuovo USER
  registerUser = (user: usersModel) => {
    console.log("-  POST /api/users/post/")
    return this.httpClient.post<usersModel>(`http://${this.server}:${this.port}/api/users/post`, user)
  }

   //Eliminazioni USER con USERNAME
   delUserByUsername = (username: string)=> {
    return this.httpClient.delete<errorMsgModel>('http://localhost:8080/api/users/delete/byUsername/' + username)
  }

}
