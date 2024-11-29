import { EventEmitter, Injectable } from '@angular/core';
import { Category } from '../../dto/category';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiCategoryUrl = "http://localhost:8080/api/categories";
  private categoryList!: Category[];
  categoryListChanged: EventEmitter<Category[] | undefined> = new EventEmitter();
  
  
  constructor(private http:HttpClient) { 
  }

  setCategoryList(categoryList: Category[]){
    this.categoryList = categoryList;
    this.categoryListChanged.emit(this.categoryList)
  }

  getCategoryList(): Category[]{
    return this.categoryList;
  }

  getCategoryAPI(): Observable<Category[]>{
    return this.http.get<Category[]>(`${this.apiCategoryUrl}`);
  }
}
