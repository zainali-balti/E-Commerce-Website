import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { signup } from '../../models/sigup';
import { UserStorageService } from '../../storage/user-storage.service';

const base_Url = "http://localhost:8080";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient,
    private userStorageService:UserStorageService
  ) { }

  public registration(signupRequest:signup):Observable<any>{
    return this.http.post(`${base_Url}/sign-up`,signupRequest); 
  }
  public login(username: string, password: string): Observable<boolean> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { username, password };

    return this.http.post(`${base_Url}/authenticate`, body, { headers, observe: 'response' }).pipe(
        map((res: any) => {
            const token = res.headers.get('authorization')?.substring(7);
            const user = res.body;
            if (token && user) {
                this.userStorageService.saveToken(token);
                this.userStorageService.saveUser(user);
                return true;
            }
            return false;
        }),
        catchError((error) => {
            console.error("Login error:", error);
            return throwError(() => new Error("Login failed"));
        })
    );
}

public getTrackingId(trackingId:any):Observable<any>{
  return this.http.get(`${base_Url}/tracking/${trackingId}`);
}


}
