import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogInsertComponent } from './dialog-insert.component';

describe('DialogInsertComponent', () => {
  let component: DialogInsertComponent;
  let fixture: ComponentFixture<DialogInsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogInsertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
