/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { RacfGroupDetailComponent } from 'app/entities/racf-group/racf-group-detail.component';
import { RacfGroup } from 'app/shared/model/racf-group.model';

describe('Component Tests', () => {
  describe('RacfGroup Management Detail Component', () => {
    let comp: RacfGroupDetailComponent;
    let fixture: ComponentFixture<RacfGroupDetailComponent>;
    const route = ({ data: of({ racfGroup: new RacfGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [RacfGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RacfGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RacfGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.racfGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
