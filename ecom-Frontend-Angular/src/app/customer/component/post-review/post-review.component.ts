import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../service/customer.service';
import { ActivatedRoute } from '@angular/router';
import { UserStorageService } from '../../../storage/user-storage.service';

@Component({
  selector: 'app-post-review',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './post-review.component.html',
  styleUrl: './post-review.component.css'
})
export class PostReviewComponent implements OnInit{
  reviewDto:any = {
    rating:0,
    description:'',
    userId:0,
    productId:0,
    img:'',
    userName:''
  }
  productId:number= 0;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  successMessage: string = '';
  errorMessage: string = '';
  constructor(private customerService:CustomerService,private route:ActivatedRoute){}
  ngOnInit(): void {
    this.productId = this.route.snapshot.params['productId'];
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
  giveMyReview(){
    this.reviewDto.userId = UserStorageService.getUserId();
    this.reviewDto.productId = this.productId;
    const formData = new FormData();
    formData.append('review', new Blob([JSON.stringify(this.reviewDto)], { type: 'application/json' }));
    if (this.selectedFile) {
      formData.append('img', this.selectedFile);
    }
    this.customerService.giveMyReviews(formData).subscribe((data:any)=>{
      console.log("Review Added successfully!",data);
      this.successMessage = "Review Added Successfully!"
  
    },(error) => {
      console.error("Error adding Review:", error);
      this.errorMessage = "Sorry Review was Not Added Some thing went Wrong!"
    })
  }
    resetForm(){
      this.reviewDto = { 
        rating:'',
        description:'',
        userId:'',
        productId:'',
        img:'',
        userName:''
      }
    }
  

}
