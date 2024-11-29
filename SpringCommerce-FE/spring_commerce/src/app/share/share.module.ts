import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomePrice3dotPipe } from '../core/pipes/custome-price-3dot.pipe';



@NgModule({
  declarations: [CustomePrice3dotPipe],
  imports: [
    CommonModule
  ],
  exports: [CustomePrice3dotPipe]
})
export class ShareModule { }
