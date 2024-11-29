import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Product } from '../../dto/product';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiProductsUrl = "http://localhost:8080/api/products";
  private productList!: Product[];
  productListChanged: EventEmitter<Product[] | undefined> = new EventEmitter();
  
  
  constructor(private http:HttpClient,private authService:AuthService) { 
  }

  setProductList(productList: Product[]){
    this.productList = productList;
    this.productListChanged.emit(this.productList)
  }

  getProductList(): Product[]{
    return this.productList;
  }

  // getProductsAPI(): Observable<Product[]>{
  //   const token = this.authService.getToken();  // Lấy JWT từ AuthService
  //   const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

  //   return this.http.get<Product[]>(`${this.apiProductsUrl}`,{ headers });
  // }

  getProductsAPI(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.apiProductsUrl}`);
  }

  getProductByIdAPI(id: number): Observable<Product>{
    return this.http.get<Product>(`${this.apiProductsUrl}/${id}`);
  }
}
