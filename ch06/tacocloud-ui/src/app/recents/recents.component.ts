import { Component, OnInit, Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'recent-tacos',
  templateUrl: 'recents.component.html',
  styleUrls: ['./recents.component.css']
})

@Injectable()
export class RecentTacosComponent implements OnInit {
  recentTacos: any;

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.httpClient.get('http://localhost:8080/design/recent') // <1>
        .subscribe(data => this.recentTacos = data);

	//In that method, RecentTacosComponent uses the injected Http module 
	//to perform an HTTP GET request to http://localhost:8080/design/recent, 
	//expecting that the response will contain a list of taco designs, 
	//which will be placed in the recentTacos model variable. 
	//The view (in recents .component.html) will present 
	//that model data as HTML to be rendered in the browser. 
	//now we need an endpoint that handles GET requests
  }
}
