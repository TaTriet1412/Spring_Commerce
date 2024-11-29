import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Product } from '../dto/product';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDetails } from '../dto/product_details';
import { ProductService } from '../core/services/product.service';
import { ProductDetailsService } from '../core/services/product-details.service';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Brand } from '../dto/brand';
import { Color } from '../dto/color';
import { Category } from '../dto/category';
import { BrandService } from '../core/services/brand.service';
import { ColorService } from '../core/services/color.service';
import { CategoryService } from '../core/services/category.service';
import { ShareModule } from '../share/share.module';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../core/services/auth.service';
import { CartService } from '../core/services/cart.service';
import { CartItemService } from '../core/services/cart-item.service';
import { SnackBarService } from '../core/services/snack-bar.service';
import { CartItem } from '../dto/cart_item';
import { Cart } from '../dto/cart';

@Component({
  selector: 'app-shop-detail',
  imports: [CommonModule,ShareModule,FormsModule],
  standalone: true,
  templateUrl: './shop-detail.component.html',
  styleUrl: './shop-detail.component.css'
})
export class ShopDetailComponent implements OnInit, AfterViewInit{
  quantity: number = 1;
  currProduct!: Product;
  productDetailList!: ProductDetails[];
  currProductDetails!: ProductDetails;
  productId!: number;
  brandList!: Brand[];
  colorList!: Color[];
  categoryList!:Category[];
  constructor(
    private activeRoute: ActivatedRoute,
    private productService: ProductService,
    private brandService: BrandService,
    private cartItemService: CartItemService,
    private snackService: SnackBarService,
    private cartService: CartService,
    private authService: AuthService,
    private colorService: ColorService,
    private router: Router,
    private categoryService: CategoryService,
    private productDetailsService: ProductDetailsService,
  ){}

  async ngAfterViewInit(): Promise<void> {
    await (this.ngOnInit());
    console.log(this.currProduct,this.currProductDetails)
  }
  
  async ngOnInit(): Promise<void> {
    (this.activeRoute.params.subscribe(params => {
      this.productId = +params['id'];
    }))
    await this.loadProduct();
    await this.loadProductDetailsList();
    await this.loadBrandList();
    await this.loadColorList();
    await this.loadCategoryList();
    this.currProductDetails = this.productDetailList.find(productDetail => productDetail.productId == this.productId)!
  }

  async loadProduct(): Promise<void>{
    this.currProduct =  await firstValueFrom(this.productService.getProductByIdAPI(this.productId));
  }

  async loadProductDetailsList(): Promise<void>{
    this.productDetailList =  await firstValueFrom(this.productDetailsService.getProductDetailsAPI())
  }

  async loadBrandList(): Promise<void>{
    const brandList = await firstValueFrom(this.brandService.getBrandAPI());
    this.brandService.setBrandList(brandList);
    this.brandList = this.brandService.getBrandList();
  }

  async loadColorList(): Promise<void>{
    const colorList = await firstValueFrom(this.colorService.getColorAPI());
    this.colorService.setColorList(colorList);
    this.colorList = this.colorService.getColorList();
  }

  async loadCategoryList(): Promise<void>{
    const categoryList = await firstValueFrom(this.categoryService.getCategoryAPI());
    this.categoryService.setCategoryList(categoryList);
    this.categoryList = this.categoryService.getCategoryList();
  }

  getCategoryByProductId(){
    return this.categoryList.find(category => category.id == this.currProduct?.categoryId);
  }

  getBrandByProductId(){
    return this.categoryList.find(category => category.id == this.currProduct?.categoryId);
  }

  getRealPrice(price: number, discount: number){
    return Math.floor(price*(1-discount/100));
  }

   // Hàm giảm số lượng
   decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  // Hàm tăng số lượng
  increaseQuantity(): void {
    if (this.quantity < this.currProductDetails.stock_quantity) {
      this.quantity++;
    }
  }
  
  addToCart(productId: number, quantity: number) {
    if(!this.authService.getUserDetails()){
      this.router.navigate(['/login_and_register'])
    }else {
      const userId = this.authService.getUserDetails().id;
      this.cartService.getCartByUserIdAPI(userId)
        .subscribe({
          next: (response: Cart) => {
            this.cartItemService.addToCart(response.id,productId,quantity)
              .subscribe({
                next: (response: CartItem) => {
                  this.snackService.notifySuccessUser("Thêm vào giỏ hàng thành công")
                  this.router.navigate(['cart'])
                },
                error: (error: any) => {
                  this.snackService.notifyErrorUser(error.message)
                }
              })
          }
        })
    } 
  }
}
