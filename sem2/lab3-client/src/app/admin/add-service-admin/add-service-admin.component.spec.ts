import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddServiceAdminComponent } from './add-service-admin.component';

describe('AddServiceAdminComponent', () => {
  let component: AddServiceAdminComponent;
  let fixture: ComponentFixture<AddServiceAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddServiceAdminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddServiceAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
