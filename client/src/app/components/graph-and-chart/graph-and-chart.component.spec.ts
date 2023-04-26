import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraphAndChartComponent } from './graph-and-chart.component';

describe('GraphAndChartComponent', () => {
  let component: GraphAndChartComponent;
  let fixture: ComponentFixture<GraphAndChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GraphAndChartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GraphAndChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
