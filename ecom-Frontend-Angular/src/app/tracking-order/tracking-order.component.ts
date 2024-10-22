import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../service/auth/auth.service';

@Component({
  selector: 'app-tracking-order',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './tracking-order.component.html',
  styleUrl: './tracking-order.component.css'
})
export class TrackingOrderComponent {
  searchTerm:any = '';
  orders:any = [];
  constructor(private authService:AuthService){}

  searchByTrackingId(){
    this.authService.getTrackingId(this.searchTerm).subscribe((data:any)=>{
      this.orders = data;
      console.log(this.orders)
    })
  }

}
