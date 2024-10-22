import { Component,OnInit } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-faq',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './post-faq.component.html',
  styleUrl: './post-faq.component.css'
})
export class PostFaqComponent implements OnInit{
  faqDto:any = {
    question:'',
    answer:'',
  }
  productId:number = 0;
  errorMessage:string = '';
  successMessage:string = '';

  constructor(private adminService:AdminService,private router:ActivatedRoute){}
  ngOnInit(): void {
    this.productId = this.router.snapshot.params['productId'];
  }

  postFAQ(productId:number,faqDto:any){
    this.adminService.postFAQ(productId,faqDto).subscribe((data:any)=>{
      faqDto = data;
      console.log("add FAQ in database!",faqDto)
      this.successMessage = "SuccessFully Added FAQ in DataBase!";
    },(error)=>{
      console.log("Something Went Wrong!",error)
      this.errorMessage = "Sorry Something Went Wrong on Server Side!";
    })
  }
  resetForm(){
    this.faqDto= {
      question:'',
      answer:'',
    }
    this.successMessage = '';
    this.errorMessage = '';
  }
}
