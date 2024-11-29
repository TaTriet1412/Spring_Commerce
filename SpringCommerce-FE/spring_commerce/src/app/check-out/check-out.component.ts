import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { SnackBarService } from '../core/services/snack-bar.service';
import { Order } from '../dto/order';
import { OrderItem } from '../dto/order_item';
import { OrderService } from '../core/services/order.service';
import { OrderItemService } from '../core/services/order-item.service';
import { AuthService } from '../core/services/auth.service';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { ProductDetails } from '../dto/product_details';
import { ProductDetailsService } from '../core/services/product-details.service';
import { Product } from '../dto/product';
import { ProductService } from '../core/services/product.service';
import { ShareModule } from '../share/share.module';

@Component({
  selector: 'app-check-out',
  imports: [CommonModule,ShareModule],
  standalone: true,
  templateUrl: './check-out.component.html',
  styleUrl: './check-out.component.css'
})
export class CheckOutComponent implements OnInit, AfterViewInit {
  currOrder!: Order;
  productDetailsList!: ProductDetails[];
  productList!: Product[];
  currOrderItemList!: OrderItem[];
  user: any;

  constructor(
    private snackBarService: SnackBarService,
    private router: Router,
    private orderService: OrderService,
    private productDetailsService:ProductDetailsService,
    private authService: AuthService,
    private productService: ProductService,
    private orderItemService: OrderItemService,
  ){}


  async ngOnInit(): Promise<void> {
    if(this.authService.getUserDetails()==null){
      this.router.navigate(['login_and_register'])
    }
    this.user = await (this.authService.getUserDetails());
    await this.loadOrder();
    await this.loadOrderItemList();
    await this.loadProductList();
    await this.loadProductDetailsList();
  }

  async loadOrder(){
    this.currOrder = await firstValueFrom(this.orderService.getOrderByUserId(this.user.id))
  }

  async loadOrderItemList(){
    this.currOrderItemList = await firstValueFrom(this.orderItemService.getOrderListByOrderId(this.currOrder.id))
  }

  async loadProductList(): Promise<void>{
    const productList = await firstValueFrom(this.productService.getProductsAPI());
    this.productService.setProductList(productList);
    this.productList = this.productService.getProductList();
  }
  

  async loadProductDetailsList(): Promise<void>{
    const productDetailsList = await firstValueFrom(this.productDetailsService.getProductDetailsAPI());
    this.productDetailsService.setProductDetailsList(productDetailsList);
    this.productDetailsList = this.productDetailsService.getProductDetailsList();
  }

  async ngAfterViewInit(): Promise<void> {
    await this.ngOnInit();
    console.log(this.currOrder,this.currOrderItemList)
  }

  getProductDetailByProductId(productId: number){
    return this.productDetailsList.find(productDetails => productDetails.productId == productId)
  }

  getProductById(productId: number){
    return this.productList.find(product => product.id == productId)
  }

  goToPayment(){
    this.snackBarService.notifySuccessUser("Hoàn tất đặt hàng");
  }
}
