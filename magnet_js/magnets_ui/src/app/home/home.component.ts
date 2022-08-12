import { Component, OnInit } from '@angular/core';
import { PubsubService } from '../pubsub.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  magneturl:string = ""

  constructor(private pubsub:PubsubService) { }

  sendMagnet(){
    
    this.pubsub.cloud_publisher_function(this.magneturl)

  }
  ngOnInit(): void {
  }

}
