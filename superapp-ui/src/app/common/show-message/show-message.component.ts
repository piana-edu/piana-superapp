import {Component, Inject, inject} from '@angular/core';
import {MAT_SNACK_BAR_DATA, MatSnackBarRef} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';

@Component({
  standalone: true,
  selector: 'app-show-message',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './show-message.component.html',
  styleUrl: './show-message.component.css'
})
export class ShowMessageComponent {
  snackBarRef = inject(MatSnackBarRef);

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: any) {
  }
}
