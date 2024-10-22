import { Routes } from '@angular/router';
import { LoginComponent } from './login/login/login.component';
import { SignupComponent } from './signup/signup/signup.component';
import { AdminComponent } from './admin/admin/admin.component';
import { DashboardComponent } from './admin/component/dashboard/dashboard.component';
import { CustomerComponent } from './customer/customer/customer.component';
import { CategoryComponent } from './admin/component/category/category.component';
import { ProductComponent } from './admin/component/product/product.component';
import { UpdateProductComponent } from './admin/component/update-product/update-product.component';
import { CustomerDashboardComponent } from './customer/component/customer-dashboard/customer-dashboard.component';
import { CardComponent } from './customer/component/card/card.component';
import { AddCouponComponent } from './admin/component/add-coupon/add-coupon.component';
import { CouponsComponent } from './admin/component/coupons/coupons.component';
import { PlacedOrderComponent } from './customer/component/placed-order/placed-order.component';
import { OrderComponent } from './admin/component/order/order.component';
import { MyOrderComponent } from './customer/component/my-order/my-order.component';
import { PostFaqComponent } from './admin/component/post-faq/post-faq.component';
import { ReviewComponent } from './customer/component/review/review.component';
import { PostReviewComponent } from './customer/component/post-review/post-review.component';
import { ViewProductDetailsComponent } from './customer/component/view-product-details/view-product-details.component';
import { WishListComponent } from './customer/component/wish-list/wish-list.component';
import { TrackingOrderComponent } from './tracking-order/tracking-order.component';

export const routes: Routes = [
    {path:'login', component:LoginComponent},
    {path:'signup', component:SignupComponent},
    {path:'tracking', component:TrackingOrderComponent},
    {path:'admin', component:AdminComponent,
        children:[
            {path:'dashboard', component:DashboardComponent},
            {path:'category', component:CategoryComponent},
            {path:'product', component:ProductComponent},
            {path:'update/:id', component:UpdateProductComponent},
            {path:'add-coupon', component:AddCouponComponent},
            {path:'coupon', component:CouponsComponent},
            {path:'order', component:OrderComponent},
            {path:'faq/:productId', component:PostFaqComponent},
            { path: '**', redirectTo: 'dashboard' }
        ]
    },
    { path: 'customer', component: CustomerComponent,
        children: [
          { path: 'customer-dashboard', component: CustomerDashboardComponent },
          { path: 'card', component: CardComponent },
          { path: 'placed-order', component: PlacedOrderComponent },
          { path: 'my-order', component: MyOrderComponent },
          { path: 'review/:orderId', component: ReviewComponent },
          { path: 'post-review/:productId', component: PostReviewComponent },
          { path: 'view/:productId', component: ViewProductDetailsComponent },
          { path: 'wish', component: WishListComponent },
          { path: '**', redirectTo: 'customer-dashboard' } 
        ]
      },
      { path: '**', redirectTo: '/customer/customer-dashboard' }
];
