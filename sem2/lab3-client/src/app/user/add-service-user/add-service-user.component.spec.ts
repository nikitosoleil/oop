import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddServiceUserComponent } from './add-service-user.component';

describe('AddServiceUserComponent', () => {
  let component: AddServiceUserComponent;
  let fixture: ComponentFixture<AddServiceUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddServiceUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddServiceUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
