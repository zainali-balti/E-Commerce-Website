import { Component,OnInit } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { CommonModule } from '@angular/common';
import { E } from '@angular/cdk/keycodes';

@Component({
  selector: 'app-coupons',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './coupons.component.html',
  styleUrl: './coupons.component.css'
})
export class CouponsComponent implements OnInit{

  coupon:any = [];

  constructor(private adminService:AdminService){}

  ngOnInit(): void {
    this.getAllCoupons();
  }
  getAllCoupons(){
    this.adminService.getAllCoupons().subscribe((data:any)=>{
      this.coupon = data;
      console.log(this.coupon)
    },(error)=>{
      console.log("Error Fetching the Coupons data!",error);
    })
  }
}
