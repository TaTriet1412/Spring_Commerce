import { EventEmitter, Injectable } from '@angular/core';
import { Brand } from '../../dto/brand';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BrandService {
  private apiBrandUrl = "http://localhost:8080/api/brands";
  private brandList!: Brand[];
  brandListChanged: EventEmitter<Brand[] | undefined> = new EventEmitter();
  
  
  constructor(private http:HttpClient) { 
  }

  setBrandList(brandList: Brand[]){
    this.brandList = brandList;
    this.brandListChanged.emit(this.brandList)
  }

  getBrandList(): Brand[]{
    return this.brandList;
  }

  getBrandAPI(): Observable<Brand[]>{
    return this.http.get<Brand[]>(`${this.apiBrandUrl}`);
  }
}
