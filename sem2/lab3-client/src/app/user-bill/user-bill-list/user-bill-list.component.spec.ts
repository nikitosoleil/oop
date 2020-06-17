import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserBillListComponent } from './user-bill-list.component';

describe('UserBillListComponent', () => {
  let component: UserBillListComponent;
  let fixture: ComponentFixture<UserBillListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserBillListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserBillListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
