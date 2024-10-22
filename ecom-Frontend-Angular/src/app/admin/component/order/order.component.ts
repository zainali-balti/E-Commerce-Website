import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css'
})
export class OrderComponent implements OnInit{
  orderDto:any = [];
  constructor(private adminService:AdminService){}
  ngOnInit(): void {
    this.getAllOrders();
  }
  getAllOrders(){
    this.adminService.getAllOrders().subscribe((data:any)=>{
      this.orderDto = data;
      console.log("Fetching the orders Successfully!",this.orderDto);
    },(error)=>{
      console.log("Error while fetching the orders!",error)
    })
  }
  chageOrderStatus(orderId:number,orderStatus:string){
    this.adminService.changeOrderStatus(orderId,orderStatus).subscribe((data:any)=>{
        this.orderDto.orderStatus = data;
    })
  }

}
