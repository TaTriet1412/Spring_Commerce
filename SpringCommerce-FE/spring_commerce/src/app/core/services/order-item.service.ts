import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { OrderItem } from '../../dto/order_item';

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {
  private apiOrderUrl = "http://localhost:8080/api/order_items";

  constructor(
    private http:HttpClient,
    private authService:AuthService,
  ) { }

  getOrderListByOrderId(orderId: number): Observable<OrderItem[]>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<OrderItem[]>(`${this.apiOrderUrl}/order/${orderId}`,{ headers });
  }
}
