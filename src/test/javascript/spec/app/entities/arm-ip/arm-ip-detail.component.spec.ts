/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmIpDetailComponent } from 'app/entities/arm-ip/arm-ip-detail.component';
import { ArmIp } from 'app/shared/model/arm-ip.model';

describe('Component Tests', () => {
  describe('ArmIp Management Detail Component', () => {
    let comp: ArmIpDetailComponent;
    let fixture: ComponentFixture<ArmIpDetailComponent>;
    const route = ({ data: of({ armIp: new ArmIp(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmIpDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArmIpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmIpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.armIp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
