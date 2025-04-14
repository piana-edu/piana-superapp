import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {DOCUMENT} from '@angular/common';
import {MatInputModule} from '@angular/material/input';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ShowOnDirtyErrorStateMatcher} from '@angular/material/core';
import {HttpClient} from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-root',
  imports: [
    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'superapp-ui';

}
