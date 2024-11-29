import { EventEmitter, Injectable } from '@angular/core';
import { ProductDetails } from '../../dto/product_details';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsService {
  private apiProductDetailsUrl = "http://localhost:8080/api/product_details";
  private productDetailsList!: ProductDetails[];
  productDetailsListChanged: EventEmitter<ProductDetails[] | undefined> = new EventEmitter();
  
  
  constructor(private http:HttpClient) { 
  }

  setProductDetailsList(productDetailsList: ProductDetails[]){
    this.productDetailsList = productDetailsList;
    this.productDetailsListChanged.emit(this.productDetailsList)
  }

  getProductDetailsList(): ProductDetails[]{
    return this.productDetailsList;
  }

  getProductDetailsAPI(): Observable<ProductDetails[]>{
    return this.http.get<ProductDetails[]>(`${this.apiProductDetailsUrl}`);
  }

  getProductDetailByIdAPI(id: number): Observable<ProductDetails>{
    return this.http.get<ProductDetails>(`${this.apiProductDetailsUrl}/${id}`);
  }

  
}
