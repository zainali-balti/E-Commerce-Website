import { Component,OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from '../../service/customer.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './review.component.html',
  styleUrl: './review.component.css'
})
export class ReviewComponent implements OnInit{
  orderProductDetailList:any = [];
  totalAmount:number = 0;
  orderId:number = 0;
  constructor(private route:ActivatedRoute,private customerService:CustomerService){}
  ngOnInit(): void {
    this.orderId = this.route.snapshot.params['orderId'];
    this.getAllMyReviews(this.orderId);
  }
  getAllMyReviews(orderId: number) {
    this.customerService.getMyReviews(orderId).subscribe(
      (data: any) => {
        console.log(data);
        // Check if productDtos exists in the response
        if (data.productDtos) {
          data.productDtos.forEach((element: { img: string; processedImg: string | null; }) => {
            element.processedImg = 'data:image/jpeg;base64,' + element.img;
            this.orderProductDetailList.push(element);
          });
        }
        this.totalAmount = data.orderAmount;
      },
      (error) => {
        console.error('Error fetching reviews:', error);
      }
    );
  }
  

}
