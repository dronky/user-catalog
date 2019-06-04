/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ZosSystemDetailComponent } from 'app/entities/zos-system/zos-system-detail.component';
import { ZosSystem } from 'app/shared/model/zos-system.model';

describe('Component Tests', () => {
  describe('ZosSystem Management Detail Component', () => {
    let comp: ZosSystemDetailComponent;
    let fixture: ComponentFixture<ZosSystemDetailComponent>;
    const route = ({ data: of({ zosSystem: new ZosSystem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ZosSystemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ZosSystemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZosSystemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.zosSystem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
