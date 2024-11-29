import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cart } from '../../dto/cart';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiCartUrl = "http://localhost:8080/api/carts";
  constructor(
    private http:HttpClient,
    private authService:AuthService,
  ) { }

  getCartByUserIdAPI(userId: number): Observable<Cart>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<Cart>(`${this.apiCartUrl}/${userId}`,{ headers });
  }
}
