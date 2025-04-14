import {Component, Inject, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ShowOnDirtyErrorStateMatcher} from '@angular/material/core';
import {AsyncPipe, DatePipe, DOCUMENT} from '@angular/common';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {MatInputModule} from '@angular/material/input';
import {Router} from '@angular/router';
import {SharedDataService} from '../../services/shared-data.service';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';

@Component({
  standalone: true,
  selector: 'app-request-otp',
    imports: [
        ReactiveFormsModule,
        MatInputModule, MatButtonModule, MatIconModule, MatTooltipModule,
        AsyncPipe, DatePipe
    ],
  templateUrl: './request-otp.component.html',
  styleUrl: './request-otp.component.css'
})
export class RequestOtpComponent implements OnInit {
  myForm: FormGroup;
  matcher = new ShowOnDirtyErrorStateMatcher();

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2,
    private router: Router,
    private fb: FormBuilder,
    private sharedData: SharedDataService,
    private httpClient: HttpClient) {
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
      captcha: ['', [Validators.required]],
      mobile: ['', [Validators.required,
        Validators.minLength(11),
        Validators.maxLength(13),
        Validators.pattern('^(\\+98|0)?9\\d{9}$')]]
    });
  }

  ngOnInit() {

  }

  resendCaptcha(e) {
    e.stopPropagation();
    this.time = new Date().getTime()
  }

  time: number = new Date().getTime();

  onSubmit(form: FormGroup) {
    if (form.valid) {
      this.httpClient.post("api/v1/auth/request-otp", {mobile: form.value.mobile, captcha: form.value.captcha},
        {
          observe: 'response',
        }).subscribe(
        (data) => {
          if (data.status === 200 || data.status === 202) {
            this.sharedData.mobileToGetOtp = form.value.mobile;
            this.router.navigate(['/auth/verify-otp']);
            console.log(`Got a successful status code: ${data.status}`);
          }
          console.log(`This contains body: ${data.body}`);
        },
        (err: HttpErrorResponse) => {
          if (err.status === 403) {
            console.error('403 status code caught');
          }
        },
      )
    }
  }
}
