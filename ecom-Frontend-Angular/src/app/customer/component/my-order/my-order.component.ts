import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../../service/customer.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-my-order',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './my-order.component.html',
  styleUrl: './my-order.component.css'
})
export class MyOrderComponent implements OnInit{
  orderDto:any = [];

  constructor(private customerService:CustomerService){}
  ngOnInit(): void {
    this.getAllOrderDetails();
  }
  getAllOrderDetails(){
    this.customerService.getMyOrder().subscribe((data:any)=>{
      this.orderDto = data;
    })
  }
}
