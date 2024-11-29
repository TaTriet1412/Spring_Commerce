import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'customePrice3dot',
  standalone: false
})
export class CustomePrice3dotPipe implements PipeTransform {
  transform(value: number): string { 
    if (value == null) { return ''; 

    } 
    return value.toLocaleString('en-US').replace(/,/g, '.');
  }
}
