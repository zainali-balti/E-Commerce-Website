import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JsonPipe } from '@angular/common';
import { AuthService } from '../../service/auth/auth.service';
import { UserStorageService } from '../../storage/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,JsonPipe],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  isPasswordVisible: boolean = false;
  signupRequest:any = {};

  constructor(private auth:AuthService,private router:Router){}

  submit() {
    this.auth.login(this.signupRequest.email, this.signupRequest.password).subscribe(
        (isLoggedIn: boolean) => {
            if (UserStorageService.isAdminLoggedIn()) {
                console.log("Successfully Login!");
                this.router.navigate(['/admin/dashboard']);

            } else if(UserStorageService.isCustomerLoggedIn()){
                console.log("Successfully Login!");
                this.router.navigate(['/customer/dashboard']);
            }
        },
        (error: any) => {
            console.error("Error during login:", error);
        }
    );
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
