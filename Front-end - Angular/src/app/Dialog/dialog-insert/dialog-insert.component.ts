import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JsonServerService } from 'src/app/Service/json-server.service';

@Component({
  selector: 'app-dialog-insert',
  templateUrl: './dialog-insert.component.html',
  styleUrls: ['./dialog-insert.component.scss']
})
export class DialogInsertComponent implements OnInit {

  bookTitle: string = "";

  snackBarDuration = 5;
  insertBooksForm!: FormGroup;
  
  constructor(private formBuilder: FormBuilder, private jsonServerService: JsonServerService,
              private snackBar: MatSnackBar) { }


  ngOnInit(): void {
    console.log(this.bookTitle)
    //Creazione INSERT FORM GROUP
    this.insertBooksForm = this.formBuilder.group({
      bookTitle: ['', Validators.required],
      authorFName: ['', Validators.required],
      authorLName: ['', Validators.required],
      releasedYear: ['', Validators.required],
      stockQuantity: ['', Validators.required],
      pages: ['', Validators.required],
    })
  }

  addBooks(){

    //Se il form ha superato la VALIDAZIONE
    if(this.insertBooksForm.valid){
      this.jsonServerService.addBooks(this.insertBooksForm.value).subscribe({
        next:(resp) => {
          this.openSnackBar();
        }
      }

      )
    }
  }

  openSnackBar() {
    this.snackBar.openFromComponent(InsertBookComponent, {
      duration: this.snackBarDuration * 1000,
    });
  }
}

export class InsertBookComponent{}
