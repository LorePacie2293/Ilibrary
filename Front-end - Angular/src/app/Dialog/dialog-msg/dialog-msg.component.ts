import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dialog-msg',
  templateUrl: './dialog-msg.component.html',
  styleUrls: ['./dialog-msg.component.scss']
})
export class DialogMsgComponent implements OnInit {

  typeAllert: boolean = false
  text: string = ""
  
  constructor() { }

  ngOnInit(): void {

  }

}
