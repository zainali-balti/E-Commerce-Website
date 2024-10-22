import { Component } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-placed-order',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './placed-order.component.html',
  styleUrl: './placed-order.component.css'
})
export class PlacedOrderComponent {
  placedOrderDto:any = {
    address:'',
    orderDescription:''
  }
  successMessage:string = '';
  errorMessage:string = '';
  constructor(private customerService:CustomerService){}
  placedOrder(placedOrderDto:any){
   this.customerService.placedOrder(placedOrderDto).subscribe((data:any)=>{
    this.placedOrderDto = data;
  console.log("Added Order Successfully!",this.placedOrderDto);
   },(error)=>{
    console.log("Sorry Can't add the Order",error);
   })
  }
  resetForm(){
    this.placedOrderDto = {
      address:'',
      orderDescription:''
    }
  }
}
