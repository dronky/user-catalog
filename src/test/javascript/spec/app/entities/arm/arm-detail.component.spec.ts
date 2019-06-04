/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmDetailComponent } from 'app/entities/arm/arm-detail.component';
import { Arm } from 'app/shared/model/arm.model';

describe('Component Tests', () => {
  describe('Arm Management Detail Component', () => {
    let comp: ArmDetailComponent;
    let fixture: ComponentFixture<ArmDetailComponent>;
    const route = ({ data: of({ arm: new Arm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.arm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
