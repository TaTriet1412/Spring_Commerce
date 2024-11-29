import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Order } from '../../dto/order';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiOrderUrl = "http://localhost:8080/api/orders";

  constructor(
    private http:HttpClient,
    private authService:AuthService,
  ) { }

  getOrderByUserId(userId: number): Observable<Order>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<Order>(`${this.apiOrderUrl}/user/${userId}`,{ headers });
  }

  processOrder(
    userId: number, 
    total_amount: number,
    delivery_address: string,
    orderItemRequests: any[]
  ): Observable<Order>{
    const token = this.authService.getToken();  // Lấy JWT từ AuthService
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.post<Order>(`${this.apiOrderUrl}`,
      {userId,total_amount,delivery_address,orderItemRequests},
      { headers });
  }
}
