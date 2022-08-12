import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PubsubService {

  publisher_function_url:string = "https://us-west2-neon-mote-358900.cloudfunctions.net/publisher_pubsub"

  constructor() { }

  cloud_publisher_function(url:string){
    let mag = new Magnet(url)
    fetch(this.publisher_function_url,
      {
        method:"post",
        body:JSON.stringify(mag.toString()),
        headers: {
          'Content-Type': 'application/json'          
        }
    })
    .then(resp => resp.json())
    .then(resp=>console.log(resp))
    

    
  }

}


class Magnet{
  url:string  
  
  public constructor(url:string){
    this.url = url
  }
  toString(){
    return {
      "url":this.url
    }
  }


}
