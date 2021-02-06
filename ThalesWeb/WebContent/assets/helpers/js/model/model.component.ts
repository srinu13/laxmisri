import { Component, OnInit , Input, SecurityContext} from '@angular/core';
import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';

@Component({
  selector: 'act-model',
  templateUrl: './model.component.html',
  styleUrls: ['./model.component.scss'],
  providers:  [ NgbActiveModal ]
})
export class ModelComponent implements OnInit {

  public manipulatedMessage: string;
  public modelType: string; 
  private modelRefrence: any = {};


  @Input() public message: string;
  @Input() public setModelType: string;

  constructor(
   
    private modalService: NgbModal,
    
  ) {}

  ngOnInit() {
  }

  ngOnChanges (){

    this.manipulatedMessage = JSON.parse(JSON.stringify(this.message)).message;
    this.modelType = JSON.parse(JSON.stringify(this.message)).modelType; 

    document.getElementById("openModal").click();
  } 

  public open(content){

    this.modelRefrence = this.modalService.open(content,{ windowClass: 'failure-modal',keyboard: false, backdrop: 'static' , size: 'lg'});

  }

  public closeModel(){
    //location.reload();
    console.log(this.modelType);
    /*if(this.modelType == "successIcon"){
    //  location.reload();
     //  this.showLoader = true;
    }*/
    this.modelRefrence.close();
  }
}
