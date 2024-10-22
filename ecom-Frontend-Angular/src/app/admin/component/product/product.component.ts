import { Component,OnInit } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule,JsonPipe,FormsModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  productDto:any = {
    name:'',
    description:'',
    price:'',
    categoryId:'',
    img:''
  }
  categoryList:any = [];
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private adminService:AdminService) {}
  onFileSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.selectedFile = event.target.files[0]; 
      this.previewImage();                      
    }
  }
  previewImage() {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    } else {
      this.imagePreview = null; 
    }
  }

  ngOnInit(): void {
    this.getAllCategory();
  }
  getAllCategory(){
     this.adminService.getAllCategory().subscribe((data:any)=>{
      this.categoryList = data;
      console.log("Fetch category Successfully!",data);
     })
  }
  addProduct(){
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(this.productDto)], { type: 'application/json' }));
    if (this.selectedFile) {
      formData.append('img', this.selectedFile);
    }
    this.adminService.addProduct(formData).subscribe((data:any)=>{
      this.productDto = data;
      this.successMessage = "Product added successfully!"; 
      console.log("Product added Successfully!",this.productDto);
    },(error) => {
      this.errorMessage = "Failed to add product: ";
      console.error("Error adding product:", error);
      this.resetForm();
    })
  }
  resetForm() {
    this.productDto = { name: '', description: '', price: '', categoryId: '', img: '' };
    this.selectedFile = null;
    this.imagePreview = null;
    this.successMessage = ''; 
    this.errorMessage = ''; 
  }
}
