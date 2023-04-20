import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginForAdminComponent } from './login-for-admin.component';

describe('LoginForAdminComponent', () => {
  let component: LoginForAdminComponent;
  let fixture: ComponentFixture<LoginForAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginForAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginForAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
