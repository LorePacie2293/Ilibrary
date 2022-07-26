import { Component, OnInit } from '@angular/core';
import { booksModel } from 'src/app/model/books';
import { BooksService } from 'src/app/Service/DataService/books.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { generiModel } from 'src/app/model/generi';
import { ThisReceiver } from '@angular/compiler';
import { HttpClient } from '@angular/common/http';
import { errorMsgModel } from 'src/app/model/errorMsg';

@Component({
  selector: 'app-dialog-update',
  templateUrl: './dialog-update.component.html',
  styleUrls: ['./dialog-update.component.scss']
})
export class DialogUpdateComponent implements OnInit {

  //Gestione immagine

  nameFile: string | undefined;
  imageReturn: any;
  selectedFile!: File
  base64Data: any;
  retrieveResonse: any;
  imageName: any;

  modality:string = "";                               //Modalità di inserimento(POST o PUT)
  msgError:string = '';
  msgSuccess: string = "Inserimento book effettuato con successo"
  showSuccess: boolean = false;
  showError: boolean = false;

  dropdownList: generiModel[] = [];
  selectedItems: generiModel[] = [];
  dropdownSettings!: IDropdownSettings;
  insert_remove: boolean = true

  bookId: number = 0;
  book: booksModel = {
    "bookId": 0,
    "bookTitle": "",
    "authorFname": "",
    "authorLname": "",
    "bookState": true,
    "releasedYear": null,
    "stockQuantity": 0,
    "pages": 0,
    "price": 0,
    "booksCategory": [],
    "imgUrl": ""
  };

  message: string | undefined;

  constructor(private booksService: BooksService, private httpClient: HttpClient) { }

  ngOnInit(): void {

    console.log("-  Apertura dialog " + this.modality)

    //MODALITA UPDATE
    if(this.modality == 'update'){
      this.booksService.getBooksById(this.bookId).subscribe({
        next: this.handleGetBookIdResponse.bind(this),
        error: this.handleGetBookIdWError.bind(this)
      })
    }

    this.initDropDownList()

  }

  handleGetBookIdResponse(response: any){
    this.book = response

     //Controlla i generi del book ricevuto ed inizializza la
     if(this.book.booksCategory.length != 0){
      this.selectedItems = this.book.booksCategory
    }
  }

  handleGetBookIdWError(error: any){
    console.log("errore")
    this.showError = true;
    this.msgError = error;
  }

  handlePutResponse(response: any){
    console.log(response)
    this.showSuccess = true;
    this.showError = false;

  }

  handlePutResponseError(error: any){
    console.log(error.error.messaggio.toString())
    this.showError = true;
    this.showSuccess = false;
    this.msgError = "Errore inserimento book: " + error.error.messaggio.toString();
  }

  onItemSelect(item: any) {
    console.log("select")
    this.book.booksCategory.push(item)
  }

  onItemDeSelect(item: any) {
    console.log("de select")
    this.selectedItems = this.selectedItems.filter(itemsSelect => itemsSelect !== item)
    this.book.booksCategory = this.selectedItems
    console.log(this.selectedItems)
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  //Inizializza la lista DROP-DOWN
  initDropDownList(){

    //Inizializza lista DROP-DOWN
    this.dropdownList = [
      { categoriaId: 1, categoriaTitle: 'Horror' },
      { categoriaId: 2, categoriaTitle: 'Fantasy' },
      { categoriaId: 3, categoriaTitle: 'Romanzo' },
      { categoriaId: 4, categoriaTitle: 'Avventura' },
      { categoriaId: 5, categoriaTitle: 'Fantascienza' }
    ];

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'categoriaId',
      textField: 'categoriaTitle',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
  }


  //Salva le modifiche del BOOK e lo invia in PUT
  save(){

    //Upload image prima di inserire il book
    this.onUpload();


    this.booksService.updateBook(this.book).subscribe(
     {
        next: this.handlePutResponse.bind(this),
        error: this.handlePutResponseError.bind(this)

      }
    )
  }

  //Inserisci nuovo BOOK POST
  insert(){

    //Upload image prima di inserire il book
    this.onUpload();

    this.booksService.insertBooks(this.book).subscribe(
      {
         next: this.handlePutResponse.bind(this),
         error: this.handlePutResponseError.bind(this)

       }
     )
  }

  //Memorizza l'immagine caricata dall'utente
  onFileChanged(event:any){
    this.selectedFile = event.target.files[0];
    console.log(this.book)
  }

  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log("Upload img")
    console.log(this.selectedFile);

    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();

    if(this.selectedFile != undefined){
      this.nameFile = this.selectedFile.name

    }

    if(this.nameFile != undefined){
      uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    }
    console.log(this.selectedFile)
    this.imageName = this.selectedFile.name;
    this.book.imgUrl = this.imageName
    console.log(this.book.imgUrl)
    //Make a call to the Spring Boot Application to save the image
    this.httpClient.post<errorMsgModel>('http://localhost:8080/api/images/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {

          this.message = 'Image uploaded successfully';
          console.log(response.body)

      }
    );

  }

   //Gets called when the user clicks on retieve image button to get the image from back end
   getImage() {
    //Make a call to Sprinf Boot to get the Image Bytes.
    this.httpClient.get('http://localhost:8080/api/images/get/' + "sds.jpg")
      .subscribe(
        res => {
          this.retrieveResonse = res;
          this.base64Data = this.retrieveResonse.picByte;
          this.imageReturn = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }
}
