import { Component,OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../service/admin.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-update-product',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.css'
})
export class UpdateProductComponent implements OnInit{

  productDto:any = {
    name:'',
    description:'',
    price:'',
    categoryId:'',
    img:''
  }
  categoryList:any = [];
  productId:number = 0;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private adminService:AdminService,private route:ActivatedRoute){}
  ngOnInit(): void {
   this.getAllCategory();
   this.productId = this.route.snapshot.params['id'];
   console.log(this.productId);
   this.getProductById();
  }

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
  getAllCategory(){
    this.adminService.getAllCategory().subscribe((data:any)=>{
     this.categoryList = data;
     console.log("Fetch category Successfully!",data);
    })
 }
 getProductById(){
  this.adminService.getProductById(this.productId).subscribe((data:any)=>{
    this.productDto = data;
    console.log("Fetching all the Products!",this.productDto);
  },(error) => {
    console.error("Error Fetching product:", error);
  })
}

updateProductDetails(){
  const formData = new FormData();
  formData.append('product', new Blob([JSON.stringify(this.productDto)], { type: 'application/json' }));
  if (this.selectedFile) {
    formData.append('img', this.selectedFile);
  }
  this.adminService.updateProduct(formData,this.productId).subscribe((data:any)=>{
    console.log("Product updated successfully!",data);
    this.successMessage = "Product Added Successfully!"

  },(error) => {
    console.error("Error adding product:", error);
    this.errorMessage = "Sorry Product was Not updated Some thing went Wrong!"
  })
}
  resetForm(){
    this.productDto = { name:'',
      description:'',
      price:'',
      categoryId:'',
      img:''
    }
  }

}
