import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../service/admin.service';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-category',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent {
  categoryDto:any = {
    name:'',
    description:''
  }
  successMessage: string = '';
  errorMessage: string = '';
  constructor(private adminService:AdminService){}
  addCategory() {
    this.adminService.addCategory(this.categoryDto).subscribe(
        (data: any) => {
            this.successMessage = "Product added successfully!";
            console.log("Added successfully category!", data);
        },
        (error: any) => {
          this.errorMessage = "Failed to add product: ";
            console.error("Error during category addition:", error);
            if (error.status === 403) {
                alert("You do not have permission to add a category.");
            } else {
                alert("An error occurred during category addition: " + error.message);
            }
        }
    );
}
resetForm(){
  this.categoryDto = { name:'',description:''}
  this.successMessage = '';
  this.errorMessage = '';
}


}
