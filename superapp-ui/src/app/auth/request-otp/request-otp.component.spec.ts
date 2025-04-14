import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestOtpComponent } from './request-otp.component';

describe('RequestOtpComponent', () => {
  let component: RequestOtpComponent;
  let fixture: ComponentFixture<RequestOtpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequestOtpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RequestOtpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
