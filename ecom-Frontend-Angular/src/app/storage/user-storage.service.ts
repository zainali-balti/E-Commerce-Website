import { Injectable } from '@angular/core';

const TOKEN = "ecom-token";
const USER = "ecom-user";

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  public saveToken(token:any):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
  }
  public saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }
  static getToken():string{
    return localStorage.getItem(TOKEN)?? '';
  }
  static getUser():any{
    const user = localStorage.getItem(USER);
    return user ? JSON.parse(user) : null;
  }
  static getUserId(): string {
    const user = localStorage.getItem(USER);
    if (user === null) {
      return '';
    }
    const parsedUser = JSON.parse(user);
    return parsedUser.userId ? parsedUser.userId : '';
  }
  static getUserRole(): string {
    const user = localStorage.getItem(USER);
    if (user === null) {
      return '';
    }
    const parsedUser = JSON.parse(user);
    return parsedUser.role ? parsedUser.role : '';
  }
  static isAdminLoggedIn():boolean{
    if(this.getToken() === null){
      return false;
    }
    const role:string = this.getUserRole();
    return role === "ADMIN" ;
  }
 static isCustomerLoggedIn():boolean{
    if(this.getToken() === null){
      return false;
    }
    const role:string = this.getUserRole();
    return role === "CUSTOMER" ;
  }
  static signOut(){
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
