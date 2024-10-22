import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../storage/user-storage.service';

const base_Url = "http://localhost:8080";
@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }

  public addCategory(categoryDto:any):Observable<any>{
    return this.http.post(`${base_Url}/api/admin/category/add`,categoryDto,{headers:this.authorizationHeader()})
  }
  public getAllCategory():Observable<any>{
    return this.http.get(`${base_Url}/api/admin/category/all`,{headers:this.authorizationHeader()})
  }
  public addProduct(formData: FormData):Observable<any>{
    const headers = this.authorizationHeader();
    return this.http.post(`${base_Url}/api/admin/product/add`,formData,{headers})
  }
  public getAllProducts():Observable<any>{
    return this.http.get(`${base_Url}/api/admin/product/all`,{headers:this.authorizationHeader()})
  }
  public getAllProductsByName(name:string):Observable<any>{
    return this.http.get(`${base_Url}/api/admin/product/all/${name}`,{headers:this.authorizationHeader()})
  }
  public deleteById(id:number):Observable<any>{
    return this.http.delete(`${base_Url}/api/admin/product/${id}`,{headers:this.authorizationHeader()})
  }
  public updateProduct(formData: FormData,id:number):Observable<any>{
    return this.http.put(`${base_Url}/api/admin/product/update/${id}`,formData,{headers:this.authorizationHeader()})
  }
  public getProductById(id:number):Observable<any>{
    return this.http.get(`${base_Url}/api/admin/product/${id}`,{headers:this.authorizationHeader()})
  }
  public addCoupon(coupon:any):Observable<any>{
    const headers = this.authorizationHeader();
    return this.http.post(`${base_Url}/api/admin/coupon/add`,coupon,{headers})
  }
  public getAllCoupons():Observable<any>{
    return this.http.get(`${base_Url}/api/admin/coupon/all`,{headers:this.authorizationHeader()})
  }
  public getAllOrders():Observable<any>{
    return this.http.get(`${base_Url}/api/admin/order/all`,{headers:this.authorizationHeader()})
  }
  public changeOrderStatus(orderId:number,orderStatus:string):Observable<any>{
    return this.http.get(`${base_Url}/api/admin/order/orderStatus/${orderId}/${orderStatus}`,{headers:this.authorizationHeader()})
  }
  public postFAQ(productId:number,faqDto:any):Observable<any>{
    return this.http.post(`${base_Url}/api/admin/faq/add/${productId}`,faqDto,{headers:this.authorizationHeader()})
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
