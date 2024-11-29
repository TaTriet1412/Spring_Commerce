import { CommonModule } from '@angular/common';
import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { AuthService } from '../core/services/auth.service';
import { async, filter, firstValueFrom } from 'rxjs';
import { Cart } from '../dto/cart';
import { CartItem } from '../dto/cart_item';
import { CartItemService } from '../core/services/cart-item.service';
import { CartService } from '../core/services/cart.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit,AfterViewInit {
  user: any;
  pageTitle: string = 'Trang chủ'; // Default page title
  activePage: string = 'shop'; // Default active page (can be 'shop', 'cart', 'checkout', etc.)
  currCart!: Cart;
  currCartItemList!: CartItem[];
  lengthOfCart: number = 0;

  constructor(
    private router: Router,
    private cartItemService: CartItemService,
    private cartService: CartService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  async ngOnInit(): Promise<void> {
    // Listen for route changes
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      )
      .subscribe(() => {
        this.updateActivePage();
      });

    if(this.authService.getUserDetails()){
      this.user = this.authService.getUserDetails()
      await this.loadCart();
      await this.loadCartItemList();
    }
    this.cdr.detectChanges();
  }

  async loadCart(): Promise<void>{
    this.currCart =  await firstValueFrom(this.cartService.getCartByUserIdAPI(this.user.id))
  }

  async loadCartItemList(): Promise<void>{
    this.currCartItemList =  await firstValueFrom(this.cartItemService.getCartItemByCartIdAPI(this.currCart.id))
  }


  async ngAfterViewInit(): Promise<void> {
    await this.ngOnInit();
  }

  updateActivePage(): void {
    const currentRoute = this.activatedRoute.snapshot.firstChild?.routeConfig?.path;
    if (currentRoute) {
      this.setActivePage(currentRoute);
    }
  }

  setActivePage(page: string): void {
    // Update the active page and page title based on the route
    switch(page) {
      case 'home':
      case 'shop':
        this.activePage = 'shop';
        this.pageTitle = 'Cửa hàng';
        this.router.navigate([this.activePage]);
        break;
      case 'cart':
        this.activePage = 'cart';
        this.pageTitle = 'Giỏ hàng';
        this.router.navigate([this.activePage]);
        break;
      case 'checkout':
        this.activePage = 'checkout';
        this.pageTitle = 'Đơn hàng';
        this.router.navigate([this.activePage]);
        break;
      case 'account':
        this.activePage = 'login_and_register';
        this.pageTitle = 'Tài khoản';
        this.router.navigate([this.activePage]);
        break;
    }
  }

  goToCart(): void {
    if (!this.authService.getUserDetails()) {
      this.router.navigate(['login_and_register']);
    } else {
      this.router.navigate(['cart']);
    }
  }
}
