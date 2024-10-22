import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../service/customer.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule,FormsModule,RouterLink],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.css'
})
export class CustomerDashboardComponent implements OnInit{
  products:any = [];
  searchTerm:string = '';

  constructor(private customerService:CustomerService){}
  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.customerService.getAllProducts().subscribe((data:any)=>{
      this.products = data;
      console.log("Fetching all the Products!",this.products);
    },(error) => {
      console.error("Error adding product:", error);
    })
  }
  search(): void {
    if (this.searchTerm.trim() === '') {
      this.getAllProducts(); 
    } else {
      this.customerService.getAllProductsByName(this.searchTerm).subscribe((data: any) => {
        this.products = data;
        console.log("Fetched Products by search term:", this.products);
        if (this.products.length === 0) {
          console.log(`No products found with the name "${this.searchTerm}"`);
        }
      }, (error) => {
        console.error("Error searching for product:", error);
      });
    }
  }
  addToCard(id: number) {
    this.customerService.addProductInCard(id).subscribe(
        (data: any) => {
            console.log("Product added to cart successfully!", data);
            alert("Product added to cart!");  // You can replace this with a more elegant UI notification
            this.getAllProducts();  // Optionally refresh the product list or cart
        },
        (error) => {
            console.error("Error adding product to cart:", error);
            alert("Failed to add product to cart!");  // Display error to the user
        }
    );
}

}
