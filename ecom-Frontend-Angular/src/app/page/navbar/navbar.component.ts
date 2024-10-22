import { Component,OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { UserStorageService } from '../../storage/user-storage.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
 
  isAdminLoggedIn:boolean = UserStorageService.isAdminLoggedIn();
  isCustomerLoggedIn:boolean = UserStorageService.isCustomerLoggedIn();
  constructor(private router:Router){}
  ngOnInit(): void {
    this.router.events.subscribe(data=>{
        this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn(); 
        this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn(); 
    })
  }
  logOut(){
    UserStorageService.signOut();
    this.router.navigateByUrl('login');
  }
}
