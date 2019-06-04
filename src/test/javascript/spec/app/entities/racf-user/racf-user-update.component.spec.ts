/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { RacfUserUpdateComponent } from 'app/entities/racf-user/racf-user-update.component';
import { RacfUserService } from 'app/entities/racf-user/racf-user.service';
import { RacfUser } from 'app/shared/model/racf-user.model';

describe('Component Tests', () => {
  describe('RacfUser Management Update Component', () => {
    let comp: RacfUserUpdateComponent;
    let fixture: ComponentFixture<RacfUserUpdateComponent>;
    let service: RacfUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [RacfUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RacfUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RacfUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RacfUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RacfUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RacfUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
