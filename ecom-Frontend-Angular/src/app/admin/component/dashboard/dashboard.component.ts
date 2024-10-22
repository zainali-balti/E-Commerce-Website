import { Component,OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{

  products:any = [];
  searchTerm:string = '';

  constructor(private adminService:AdminService){}
  ngOnInit(): void {
    this.getAllProducts();
  }
  getAllProducts(){
    this.adminService.getAllProducts().subscribe((data:any)=>{
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
      this.adminService.getAllProductsByName(this.searchTerm).subscribe((data: any) => {
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
  deleteById(id: number) {
    // Show a confirmation pop-up
    const confirmDelete = confirm("Are you sure you want to delete this product?");
  
    if (confirmDelete) {
      this.adminService.deleteById(id).subscribe((data: any) => {
        console.log("Deleted the Product!", data);
        this.getAllProducts(); 
      }, (error) => {
        console.error("Error deleting product:", error);
      });
    } else {
      console.log("Product deletion was canceled.");
    }
  }
  

}
