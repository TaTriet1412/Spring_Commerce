import { EventEmitter, Injectable } from '@angular/core';
import { Color } from '../../dto/color';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ColorService {
  private apiColorUrl = "http://localhost:8080/api/colors";
  private colorList!: Color[];
  colorListChanged: EventEmitter<Color[] | undefined> = new EventEmitter();
  
  
  constructor(private http:HttpClient) { 
  }

  setColorList(colorList: Color[]){
    this.colorList = colorList;
    this.colorListChanged.emit(this.colorList)
  }

  getColorList(): Color[]{
    return this.colorList;
  }

  getColorAPI(): Observable<Color[]>{
    return this.http.get<Color[]>(`${this.apiColorUrl}`);
  }
}
