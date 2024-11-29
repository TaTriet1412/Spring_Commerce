import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop/shop.component';
import { ShopDetailComponent } from './shop-detail/shop-detail.component';
import { LoginAndRegisterComponent } from './login-and-register/login-and-register.component';
import { CartComponent } from './cart/cart.component';
import { CheckOutComponent } from './check-out/check-out.component';

const routes: Routes = [
  {path: '', redirectTo: 'shop', pathMatch: 'full'},
  {path: 'home', redirectTo: 'shop', pathMatch: 'full'},
  {path: 'shop', component: ShopComponent},
  {path: 'shop-detail/:id', component: ShopDetailComponent},
  {path: 'login_and_register', component: LoginAndRegisterComponent},
  {path: 'cart', component: CartComponent},
  {path: 'checkout', component: CheckOutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
