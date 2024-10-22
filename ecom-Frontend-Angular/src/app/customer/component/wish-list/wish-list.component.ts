import { Component,OnInit } from '@angular/core';
import { CustomerService } from '../../service/customer.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-wish-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wish-list.component.html',
  styleUrl: './wish-list.component.css'
})
export class WishListComponent implements OnInit{
  products:any = [];

  constructor(private customerService:CustomerService){}
  ngOnInit(): void {
    this.getAllWishList();
  }
  getAllWishList() {
    this.customerService.getAllWishList().subscribe((data: any) => {
      console.log('WishList Data:', data);  // Log the data to check it
      data.forEach((element: { processedImg: string; returnedImg: string; }) => {
        element.processedImg = 'data:image/jpeg;base64,' + element.returnedImg;
        this.products.push(element);
      });
    });
  }
  

}
