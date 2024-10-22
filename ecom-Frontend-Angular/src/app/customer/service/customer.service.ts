import { Injectable } from '@angular/core';
import { UserStorageService } from '../../storage/user-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


const base_Url = 'http://localhost:8080';
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }
  
  public getAllProducts():Observable<any>{
    return this.http.get(`${base_Url}/api/customer/product/all`,{headers:this.authorizationHeader()})
  }
  public getAllProductsByName(name:string):Observable<any>{
    return this.http.get(`${base_Url}/api/customer/product/all/${name}`,{headers:this.authorizationHeader()})
  }
  public addProductInCard(productId:number):Observable<any>{
    const cardDto = {
      productId: productId,
      userId: UserStorageService.getUserId()
    }
    return this.http.post(`${base_Url}/api/customer/card/add`,cardDto,{headers:this.authorizationHeader()})
  }
  public getCardByUserId():Observable<any>{
    const userId = UserStorageService.getUserId();
    return this.http.get(`${base_Url}/api/customer/card/all/${userId}`,{headers:this.authorizationHeader()})
  }
  public applyCoponCode(code:string):Observable<any>{
    const userId = UserStorageService.getUserId();
    return this.http.get(`${base_Url}/api/customer/card/all/${userId}/${code}`,{headers:this.authorizationHeader()})
  }
  public increaseQuantity(productId:any):Observable<any>{
    const addProductInCardDto = {
      productId: productId,
      userId: UserStorageService.getUserId()
    }
    return this.http.post(`${base_Url}/api/customer/card/addition`,addProductInCardDto,{headers:this.authorizationHeader()})
  }
  public decreaseQuantity(productId:any):Observable<any>{
    const addProductInCardDto = {
      productId: productId,
      userId: UserStorageService.getUserId()
    }
    return this.http.post(`${base_Url}/api/customer/card/subtraction`,addProductInCardDto,{headers:this.authorizationHeader()})
  }
  public placedOrder(placedOrderDto:any):Observable<any>{
    placedOrderDto.userId = UserStorageService.getUserId();
    return this.http.post(`${base_Url}/api/customer/card/placedOrder`,placedOrderDto,{headers:this.authorizationHeader()});
  }
  public getMyOrder():Observable<any>{
    const userId = UserStorageService.getUserId();
    return this.http.get(`${base_Url}/api/customer/card/myOrder/${userId}`,{headers:this.authorizationHeader()})
  }
  public getMyReviews(orderId:number):Observable<any>{
    return this.http.get(`${base_Url}/api/customer/review/${orderId}`,{headers:this.authorizationHeader()})
  }
  public giveMyReviews(formData: FormData):Observable<any>{
    return this.http.post(`${base_Url}/api/customer/review/add`,formData,{headers:this.authorizationHeader()})
  }
  public ViewAllProductDetails(productId:number):Observable<any>{
    return this.http.get(`${base_Url}/api/customer/product/details/${productId}`,{headers:this.authorizationHeader()})
  }
  public addProductIntoWishList(wishListDto:any):Observable<any>{
    return this.http.post(`${base_Url}/api/customer/wishlist/add`,wishListDto,{headers:this.authorizationHeader()})
  }
  public getAllWishList():Observable<any>{
    const userId = UserStorageService.getUserId();
    return this.http.get(`${base_Url}/api/customer/wishlist/all/${userId}`,{headers:this.authorizationHeader()})
  }
  private authorizationHeader():HttpHeaders{
    const token = UserStorageService.getToken();
    console.log(token);
    if (!token) {
      throw new Error('No authorization token found!');
    }
    return new HttpHeaders().set('Authorization', 'Bearer '+token);
  }
}
