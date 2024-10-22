import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JsonPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { signup } from '../../models/sigup';
import { AuthService } from '../../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule,JsonPipe,FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  isPasswordVisible: boolean = false;
  signupRequest = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private auth:AuthService,private router:Router){
  }

  submit(){
    this.auth.registration(this.signupRequest).subscribe((data:any)=>{
      this.signupRequest = data;
      console.log("Customer is Created Successfully!");
      this.router.navigateByUrl("/login");
    },
    (error)=>{
      console.log("Error is generated!");
    }
  )
  }


  togglePassword(): void {
      const passwordField = document.getElementById('password') as HTMLInputElement;
      const icon = document.querySelector('.eye-icon') as HTMLElement;

      if (passwordField) {
          if (this.isPasswordVisible) {
              passwordField.type = 'password';
              icon.textContent = '👁️';
          } else {
              passwordField.type = 'text';
              icon.textContent = '🙈';
          }
          this.isPasswordVisible = !this.isPasswordVisible;
      }
  }

}
