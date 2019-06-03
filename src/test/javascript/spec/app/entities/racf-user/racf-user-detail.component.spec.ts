/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { RacfUserDetailComponent } from 'app/entities/racf-user/racf-user-detail.component';
import { RacfUser } from 'app/shared/model/racf-user.model';

describe('Component Tests', () => {
  describe('RacfUser Management Detail Component', () => {
    let comp: RacfUserDetailComponent;
    let fixture: ComponentFixture<RacfUserDetailComponent>;
    const route = ({ data: of({ racfUser: new RacfUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [RacfUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RacfUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RacfUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.racfUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
