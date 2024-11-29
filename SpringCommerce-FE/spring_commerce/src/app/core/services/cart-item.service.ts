import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { CartItem } from '../../dto/cart_item';

@Injectable({
  providedIn: 'root'
})
export class CartItemService {
  private apiCartItemUrl = "http://localhost:8080/api/cart_items";
  constructor(
    private http:HttpClient,
    private authService:AuthService,
  ) { }

  getCartItemByCartIdAPI(cartId: number): Observable<CartItem[]>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<CartItem[]>(`${this.apiCartItemUrl}/cart/${cartId}`,{ headers });
  }

  addToCart(cartId: number,productId: number, quantity: number): Observable<CartItem>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post<CartItem>(`${this.apiCartItemUrl}`,
        {cartId,productId,quantity},{ headers });
  }

  updateCartItem(cartId: number,productId: number, quantity: number): Observable<CartItem>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.put<CartItem>(`${this.apiCartItemUrl}`,
        {cartId,productId,quantity},{ headers });
  }

  deleteCartItem(cartItemId: number): Observable<CartItem>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.delete<CartItem>(`${this.apiCartItemUrl}/${cartItemId}`,
        { headers });
  }
}
