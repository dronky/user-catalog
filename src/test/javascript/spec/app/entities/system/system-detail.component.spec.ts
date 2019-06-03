/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { SystemDetailComponent } from 'app/entities/system/system-detail.component';
import { System } from 'app/shared/model/system.model';

describe('Component Tests', () => {
  describe('System Management Detail Component', () => {
    let comp: SystemDetailComponent;
    let fixture: ComponentFixture<SystemDetailComponent>;
    const route = ({ data: of({ system: new System(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [SystemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SystemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.system).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
