import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-add-coupon',
  standalone: true,
  imports: [FormsModule,CommonModule,MatDatepickerModule,MatInputModule,MatNativeDateModule],
  templateUrl: './add-coupon.component.html',
  styleUrl: './add-coupon.component.css'
})
export class AddCouponComponent {
  successMessage:string = '';
  errorMessage:string = '';
  coupon:any = {
    name:'',
    code:'',
    discount:'',
    expirationDate:''
  }
  constructor(private adminService:AdminService){}

  addCoupon(){
    this.adminService.addCoupon(this.coupon).subscribe((data:any)=>{
      this.coupon = data;
      console.log("Add coupon!",this.coupon);
      this.successMessage = "Coupon was Added successfully!";
    },(error)=>{
      console.log("Error was found!")
      this.errorMessage = "Sorry We Don't Add The Coupon";
    })
  }
  resetForm(){
    this.coupon = { 
      name:'',
      code:'',
      discount:'',
      expirationDate:''
    }
  }

}
