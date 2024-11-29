import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ProductService } from '../core/services/product.service';
import { Product } from '../dto/product';
import { firstValueFrom } from 'rxjs';
import { CommonModule } from '@angular/common';
import { ProductDetailsService } from '../core/services/product-details.service';
import { ProductDetails } from '../dto/product_details';
import { ShareModule } from '../share/share.module';
import { Brand } from '../dto/brand';
import { Color } from '../dto/color';
import { Category } from '../dto/category';
import { BrandService } from '../core/services/brand.service';
import { ColorService } from '../core/services/color.service';
import { CategoryService } from '../core/services/category.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';
import { CartItemService } from '../core/services/cart-item.service';
import { CartService } from '../core/services/cart.service';
import { Cart } from '../dto/cart';
import { CartItem } from '../dto/cart_item';
import { SnackBarService } from '../core/services/snack-bar.service';

@Component({
  selector: 'app-shop',
  imports: [CommonModule,ShareModule,FormsModule],
  standalone: true,
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent implements OnInit, AfterViewInit{
  @ViewChild('startPriceInput') startPriceInput: ElementRef | undefined;
  @ViewChild('endPriceInput') endPriceInput: ElementRef | undefined;
  @ViewChild('nameSearchInput') nameSearchInput: ElementRef | undefined;
  productList!: Product[];
  filterProductList!: Product[];
  productDetailsList!: ProductDetails[];
  brandList!: Brand[];
  colorList!: Color[];
  categoryList!:Category[];
  selectedBrandIndex: number[] = [];
  selectedColorIndex: number[] = [];
  selectedCategoryIndex: number[] = [];
  showQuantity: boolean = true;

  constructor(
    private productService: ProductService,
    private brandService: BrandService,
    private colorService: ColorService,
    private categoryService: CategoryService,
    private cartItemService: CartItemService,
    private snackService: SnackBarService,
    private productDetailsService:ProductDetailsService,
    private cartService: CartService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ){}

  async ngOnInit(): Promise<void> {
    await this.loadProductList();
    await this.loadProductDetailsList();
    await this.loadBrandList();
    await this.loadColorList();
    await this.loadCategoryList();
  }

  async ngAfterViewInit(): Promise<void> {
    await (this.ngOnInit());
    console.log(this.productDetailsList)
  }

  async loadProductList(): Promise<void>{
    const productList = await firstValueFrom(this.productService.getProductsAPI());
    this.productService.setProductList(productList);
    this.productList = this.productService.getProductList();
    this.filterProductList = this.productList
  }

  async loadProductDetailsList(): Promise<void>{
    const productDetailsList = await firstValueFrom(this.productDetailsService.getProductDetailsAPI());
    this.productDetailsService.setProductDetailsList(productDetailsList);
    this.productDetailsList = this.productDetailsService.getProductDetailsList();
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

  getProductDetailsByProductId(id: number){
    return this.productDetailsList.find(productDetails => productDetails.productId == id);
  }

  getCategoryByProductId(id: number){
    const product = this.productList.find(product => product.id == id);
    return this.categoryList.find(category => category.id == product?.categoryId);
  }
  
  // Cập nhật hàm lọc sản phẩm
  filterProducts() {
    const searchTerm = this.nameSearchInput?.nativeElement.value.toLowerCase();
    const normalizedSearchTerm = this.normalizeVietnamese(searchTerm);

    // Helper function to calculate the discounted price
    const getDiscountedPrice = (product: { price: number; discount: number }) => {
      return product.price * (1 - product.discount / 100);
    };

    // Lọc sản phẩm theo các điều kiện: danh mục, thương hiệu, màu sắc, giá và tên
    this.filterProductList = this.productList.filter(product =>
      (this.selectedCategoryIndex.length === 0 || this.selectedCategoryIndex.includes(product.categoryId)) &&
      (this.selectedBrandIndex.length === 0 || this.selectedBrandIndex.includes(product.brandId)) &&
      (this.selectedColorIndex.length === 0 || this.selectedColorIndex.includes(product.colorId)) &&
      getDiscountedPrice(product) >= (this.startPriceInput?.nativeElement.value || 0) &&
      getDiscountedPrice(product) <= (this.endPriceInput?.nativeElement.value || 500000000) &&
      this.normalizeVietnamese(product.name.toLowerCase()).includes(normalizedSearchTerm)
    );

    // Nếu không có bộ lọc nào được chọn, hiển thị tất cả sản phẩm
    if (this.selectedCategoryIndex.length === 0 && this.selectedBrandIndex.length === 0 && this.selectedColorIndex.length === 0 &&
      parseFloat(this.startPriceInput?.nativeElement.value || "0") === 0 && parseFloat(this.endPriceInput?.nativeElement.value || "500000000") === 500000000 &&
      normalizedSearchTerm === '') {
      this.filterProductList = this.productList;
    }

    // Chỉ hiển thị số lượng khi không chọn bộ lọc nào ngoài giá và tên
    this.showQuantity = this.selectedCategoryIndex.length === 0 && this.selectedBrandIndex.length === 0 &&
                        this.selectedColorIndex.length === 0 && parseFloat(this.startPriceInput?.nativeElement.value || "0") === 0 &&
                        parseFloat(this.endPriceInput?.nativeElement.value || "500000000") === 500000000 &&
                        normalizedSearchTerm === '';

    this.cdr.detectChanges(); // Cập nhật giao diện
  }


  // Hàm chọn danh mục và lọc lại danh sách sản phẩm
  selectCategory(categoryId: number) {
    // Thêm hoặc xóa danh mục khỏi danh sách đã chọn
    if (this.selectedCategoryIndex?.some(cateId => cateId == categoryId)) {
      this.selectedCategoryIndex = this.selectedCategoryIndex.filter(catId => catId != categoryId);
    } else {
      this.selectedCategoryIndex?.push(categoryId);
    }
    // Lọc lại sản phẩm
    this.filterProducts();
  }

  // Hàm chọn thương hiệu và lọc lại danh sách sản phẩm
  selectBrand(brand_id: number) {
    // Thêm hoặc xóa thương hiệu khỏi danh sách đã chọn
    if (this.selectedBrandIndex?.some(brandId => brandId == brand_id)) {
      this.selectedBrandIndex = this.selectedBrandIndex.filter(brandId => brandId != brand_id);
    } else {
      this.selectedBrandIndex?.push(brand_id);
    }
    // Lọc lại sản phẩm
    this.filterProducts();
  }

  // Hàm chọn màu sắc và lọc lại danh sách sản phẩm
  selectColor(color_id: number) {
    // Thêm hoặc xóa màu sắc khỏi danh sách đã chọn
    if (this.selectedColorIndex?.some(colorId => colorId == color_id)) {
      this.selectedColorIndex = this.selectedColorIndex.filter(colorId => colorId != color_id);
    } else {
      this.selectedColorIndex?.push(color_id);
    }
    // Lọc lại sản phẩm
    this.filterProducts();
  }

  // Hàm lọc theo giá
  searchPrices() {
    this.filterProducts();
  }

  // Hàm lọc theo tên sản phẩm
  filterProductListByName() {
    this.filterProducts();
  }

  normalizeVietnamese(str: string): string {
    const from = 'áàảãạâấầẩẫạăắằẳẵặđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ';
    const to = 'aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiiooooooooooooooooouuuuuuuuuuyyyyyy';
    let normalized = str.split('').map(char => {
      const index = from.indexOf(char);
      return index !== -1 ? to[index] : char;
    }).join('');
    return normalized;
  }

// Hàm áp dụng sắp xếp
  applySorting(event: Event) {
    const selectedOption = (event.target as HTMLSelectElement).value;
    // Helper function to calculate the discounted price
    const getDiscountedPrice = (product: { price: number; discount: number }) => {
      return product.price * (1 - product.discount / 100);
  };

  switch (selectedOption) {
    case 'priceAsc':
      // Sort based on discounted price in ascending order
      this.filterProductList.sort((a, b) => getDiscountedPrice(a) - getDiscountedPrice(b));
      break;
    case 'priceDesc':
      // Sort based on discounted price in descending order
      this.filterProductList.sort((a, b) => getDiscountedPrice(b) - getDiscountedPrice(a));
      break;
    case 'nameAsc':
      // Sort based on normalized name (ignoring case and accents)
      this.filterProductList.sort((a, b) =>
        this.normalizeVietnamese(a.name.toLowerCase()).localeCompare(this.normalizeVietnamese(b.name.toLowerCase()))
      );
      break;
    case 'nameDesc':
      // Sort based on normalized name (ignoring case and accents), in reverse order
      this.filterProductList.sort((a, b) =>
        this.normalizeVietnamese(b.name.toLowerCase()).localeCompare(this.normalizeVietnamese(a.name.toLowerCase()))
      );
      break;
    default:
      break;
  }

    this.cdr.detectChanges(); // Cập nhật giao diện sau khi sắp xếp
  }


  checkCateId(id: number){
    return this.selectedCategoryIndex?.some(cateId => cateId == id);
  }

  checkBrandId(id: number){
    return this.selectedBrandIndex?.some(brandId => brandId == id);
  }

  checkColorId(id: number){
    return this.selectedColorIndex?.some(colorId => colorId == id);
  }

  // Số lượng sản phẩm theo category
  getQuantityOfProductByCategoryId(categoryId: number){
    const productList = this.productList.filter(product => product.categoryId == categoryId)
    return productList.length;
  }

  goToDetailProduct(id: number){
    this.router.navigate([`/shop-detail/${id}`]);
  }

  getRealPrice(price: number, discount: number){
    return Math.floor(price*(1-discount/100));
  }

  addToCart(productId: number) {
    if(!this.authService.getUserDetails()){
      this.router.navigate(['/login_and_register'])
    }else {
      const userId = this.authService.getUserDetails().id;
      this.cartService.getCartByUserIdAPI(userId)
        .subscribe({
          next: (response: Cart) => {
            this.cartItemService.addToCart(response.id,productId,1)
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
