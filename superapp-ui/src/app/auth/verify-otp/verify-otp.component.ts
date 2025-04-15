import {Component, Inject, Input, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {ShowOnDirtyErrorStateMatcher} from '@angular/material/core';
import {AsyncPipe, DatePipe, DOCUMENT} from '@angular/common';
import {HttpClient} from '@angular/common/http';
import {map, takeWhile, timer} from 'rxjs';
import {SharedDataService} from '../../services/shared-data/shared-data.service';

@Component({
  standalone: true,
  selector: 'app-verify-otp',
  imports: [
    ReactiveFormsModule,
    MatInputModule, MatIconModule, MatButtonModule,
    AsyncPipe,
    DatePipe
  ],
  templateUrl: './verify-otp.component.html',
  styleUrl: './verify-otp.component.css'
})
export class VerifyOtpComponent {
  myForm: FormGroup;
  matcher = new ShowOnDirtyErrorStateMatcher();
  @Input() seconds = 10;

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2,
    private fb: FormBuilder,
    public sharedData: SharedDataService,
    private httpClient: HttpClient) {
    this.seconds = sharedData.mobileToGetOtp.expireAt - new Date().getTime();

    this.renderer.addClass(this.document.body, 'full-height');
    this.renderer.addClass(this.document.body, 'd-flex');
    this.renderer.addClass(this.document.body, 'align-items-center');
    this.renderer.addClass(this.document.body, 'py-4');
    this.renderer.addClass(this.document.body, 'bg-body-tertiary');
    let appRoot = this.document.getElementsByTagName('app-root');
    appRoot[0].classList.add('full-width');
    // console.log(appRoot)
    let html = this.document.getElementsByTagName('html');
    html[0].classList.add('full-height');

    this.myForm = this.fb.group({
      otp: ['', [Validators.required,
        Validators.minLength(6),
        Validators.maxLength(6)]]
    });
  }

  timeRemaining$ = timer(0, 1000).pipe(
    map(n => (this.seconds - n)),
    takeWhile(n => n >= 0),
  );

  ngOnInit() {
  }

  resend(e) {
    e.stopPropagation();
    this.seconds = 10;
  }

  onSubmit(form: FormGroup) {
    if (form.valid) {
      this.httpClient.post("api/v1/auth/verify-otp", {otp: form.value.otp}).subscribe(config => {
        console.log(config)
      })
    }
  }

  protected readonly JSON = JSON;
}
