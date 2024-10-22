import { Component,OnInit } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { UserStorageService } from '../../../storage/user-storage.service';

@Component({
  selector: 'app-view-product-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-product-details.component.html',
  styleUrl: './view-product-details.component.css'
})
export class ViewProductDetailsComponent implements OnInit{
  productId:number =0; 
  products:any = [];
  FAQs:any = [];
  Reviews:any = [];
  wishlist: boolean = false;

  constructor(private customerService:CustomerService,private route:ActivatedRoute){}
  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
    this.getViewAllProductDetails();
  }
  getViewAllProductDetails(){
      this.customerService.ViewAllProductDetails(this.productId).subscribe((data:any)=>{
        this.products = data.productDto;
        this.products.processedImg = 'data:image/jpeg;base64,' + data.productDto.img;
        this.FAQs = data.faqDtos || [];
        this.Reviews = data.reviewDtos || [];
        this.Reviews.forEach((element: { processedImg: string; returnedImg: string; })=>{
          element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        })
        console.log("Products:", this.products);
        console.log("FAQs:", this.FAQs);
        console.log("Reviews:", this.Reviews);
      },(error)=>{
        console.log("Sorry Something Went Wrong During Fetching The View Product Details List")
      })
  }
  toggleWishlist() {
    this.wishlist = !this.wishlist;
  }
  addProductToCustomerWishList(){
    this.toggleWishlist();
    const wishListDto = {
      productId : this.productId,
      userId : UserStorageService.getUserId()
    }
    this.customerService.addProductIntoWishList(wishListDto).subscribe((data:any)=>{
        if(data.id != null){
          console.log("added into wishlist");
        }
        else{
          console.log("sorry somrthing went wrong")
        }
    })
  }
}
