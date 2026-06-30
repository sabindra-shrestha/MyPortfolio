import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Contact {
    name: string;
    email: string;
    subject: string;
    message: string;
  }

@Injectable({
    providedIn: 'root'
  })


  export class ContactService{
      private apiUrl = 'http://localhost:8080/api/v1/contact';

      constructor(private http: HttpClient){}

      submitContact(contact: Contact): Observable<any> {
        return this.http.post(this.apiUrl, contact);
        }
  }

