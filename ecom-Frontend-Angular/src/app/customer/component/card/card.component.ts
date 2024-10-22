import { Component,OnInit } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent  implements OnInit{

 
  cardItems: any = [];
  orderDto: any;
  coupon:any = [];
  addProductInCardDto:any = {
    userId:0,
    productId:0
  }

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.getCardByUserId();
  }
  
  getCouponCodeAuthentication(code: string) {
    if (!code || code.trim() === "") {
      console.log("Coupon code is missing or empty.");
      return;
    }
    console.log("Sending coupon code:", code); 
  
    this.customerService.applyCoponCode(code).subscribe(
      (data: any) => {
        this.coupon = data;
        console.log("Coupon applied successfully!", this.coupon);
      },
      (error) => {
        console.log("Error applying coupon:", error);
      }
    );
  }
  getCardByUserId() {
    this.cardItems = [];
    this.customerService.getCardByUserId().subscribe(
      (data: any) => {
        this.orderDto = data;
        console.log("Order Data: ", this.orderDto);
        if (this.orderDto && Array.isArray(this.orderDto.cardItems)) {
          this.orderDto.cardItems.forEach((element: { returnedImg: string; processedImg: string | null; }) => {
            if (element.returnedImg) {
              element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
            } else {
              element.processedImg = null;
            }
            this.cardItems.push(element);
          });
          console.log("Card Items:", this.cardItems);
        } else {
          console.log("No card items found.");
        }
      },
      (error) => {
        console.log("Error while fetching the card!", error);
      }
    );
  }
  increaseQuantity(productId:any){
        this.customerService.increaseQuantity(productId).subscribe((data:any)=>{
          this.addProductInCardDto = data;
          console.log("Increase The Product Quantity!",this.cardItems.quantity);
          this.getCardByUserId();
        },(error)=>{
          console.log("Error While increasing the Product Quantity!",error);
        })
  }
  decreseQuantity(productId:any){
    this.customerService.decreaseQuantity(productId).subscribe((data:any)=>{
      this.addProductInCardDto = data;
      console.log("Increase The Product Quantity!",this.cardItems.quantity);
      this.getCardByUserId();
    },(error)=>{
      console.log("Error While increasing the Product Quantity!",error);
    })
  }
  
}
