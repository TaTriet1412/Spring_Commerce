import { CommonModule } from '@angular/common';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';
import { Cart } from '../dto/cart';
import { CartService } from '../core/services/cart.service';
import { firstValueFrom } from 'rxjs';
import { CartItem } from '../dto/cart_item';
import { CartItemService } from '../core/services/cart-item.service';
import { ProductService } from '../core/services/product.service';
import { Product } from '../dto/product';
import { ShareModule } from '../share/share.module';
import { ProductDetails } from '../dto/product_details';
import { ProductDetailsService } from '../core/services/product-details.service';
import { SnackBarService } from '../core/services/snack-bar.service';
import { OrderService } from '../core/services/order.service';

@Component({
  selector: 'app-cart',
  imports: [CommonModule,ShareModule],
  standalone: true,
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit, AfterViewInit {
  user: any;
  productDetailsList!: ProductDetails[];
  productList!: Product[];
  currCart!: Cart;
  currCartItemList!: CartItem[];

  constructor(
    private router: Router,
    private authService: AuthService,
    private productService: ProductService,
    private cartService: CartService,
    private productDetailsService:ProductDetailsService,
    private cartItemService: CartItemService,
    private orderService: OrderService,
    private snackService: SnackBarService,
  ){}

  async ngOnInit(): Promise<void> {
    if(this.authService.getUserDetails()==null){
      this.router.navigate(['login_and_register'])
    }
    this.user = await (this.authService.getUserDetails());
    await this.loadCart();
    await this.loadCartItemList();
    await this.loadProductList();
    await this.loadProductDetailsList();
  }

  async loadCart(): Promise<void>{
    this.currCart =  await firstValueFrom(this.cartService.getCartByUserIdAPI(this.user.id))
  }

  async loadCartItemList(): Promise<void>{
    this.currCartItemList =  await firstValueFrom(this.cartItemService.getCartItemByCartIdAPI(this.currCart.id))
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
    await (this.ngOnInit());
  }

  getProductById(id: number){
    return this.productList.find(product => product.id == id);
  }

  getProductDetailByProductId(id: number){
    return this.productDetailsList.find(productDetail => productDetail.id == id);
  }

  getRealPrice(price: number, discount: number){
    return Math.floor(price*(1-discount/100));
  }

  // Hàm tính tổng cho một sản phẩm
  calculateTotal(cartItem: any): number {
    const product = this.getProductById(cartItem.productId);
    const realPrice = this.getRealPrice(product?.price!, product?.discount!);
    return realPrice * cartItem.quantity;
  }

  increaseQuantity(index: number,productId: number) {
    const cartItem = this.currCartItemList[index];
    const product = this.getProductById(cartItem.productId);
    const product_detail = this.getProductDetailByProductId(product!.id) 
    
    // Kiểm tra nếu số lượng hiện tại còn ít hơn số lượng tồn kho
    if (cartItem.quantity < product_detail?.stock_quantity!) {
      cartItem.quantity++;
      this.cartItemService
      .updateCartItem(this.currCart.id,productId,cartItem.quantity)
      .subscribe()
    } else {
      // Hiển thị thông báo nếu số lượng đã đạt đến giới hạn tồn kho
      this.snackService.notifyWarningUser(`Số lượng sản phẩm không thể vượt quá ${product_detail?.stock_quantity!}`);
    }
  }
  // Hàm giảm số lượng
  decreaseQuantity(index: number, productId: number) {
    if (this.currCartItemList[index].quantity > 1) {
      this.currCartItemList[index].quantity--;
      this.cartItemService
      .updateCartItem(this.currCart.id,productId,this.currCartItemList[index].quantity)
      .subscribe()
    }
  }

  // Cập nhật số lượng khi người dùng nhập trực tiếp
  updateQuantity(event: any, index: number) {
    let newQuantity = parseInt(event.target.value, 10);
    if (newQuantity > 0) {
      this.currCartItemList[index].quantity = newQuantity;
    } else {
      this.currCartItemList[index].quantity = 1; // Tránh số lượng bằng 0 hoặc âm
    }
  }

  // Hàm xóa sản phẩm khỏi giỏ hàng
  removeItem(index: number,cartItemId: number) {
    this.currCartItemList.splice(index, 1);
    this.cartItemService.deleteCartItem(cartItemId).subscribe();
  }

  // Hàm tính tổng giá trị của tất cả các sản phẩm trong giỏ hàng
  calculateCartTotal(): number {
    return this.currCartItemList.reduce((total, cartItem) => {
      const product = this.getProductById(cartItem.productId);
      const realPrice = this.getRealPrice(product?.price!, product?.discount!);
      return total + (realPrice * cartItem.quantity); // Cộng dồn tổng giá trị
    }, 0);  
  }

  processOrder() {
    // Tạo một danh sách các sản phẩm từ các item trong giỏ hàng
    const productList = this.currCartItemList.map(currCartItem => {
      const product = this.getProductById(currCartItem.productId);
      const realPrice = this.getRealPrice(product?.price!, product?.discount!);
      return {
        productId: currCartItem.productId,
        quantity: currCartItem.quantity,
        price: realPrice, // Giá thực tế của sản phẩm
      };
    });
  
    // Thực hiện xử lý đơn hàng với productList, tổng giá trị và địa chỉ giao hàng
    this.orderService.processOrder(this.user.id, this.calculateCartTotal(), "Quận 7", productList)
      .subscribe({
        next: (response:any) => {
          this.snackService.notifySuccessUser("Xác nhận thành công");
          this.router.navigate(['checkout'])
        }
      });
  }
}
