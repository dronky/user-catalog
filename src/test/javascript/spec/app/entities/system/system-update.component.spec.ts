/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { SystemUpdateComponent } from 'app/entities/system/system-update.component';
import { SystemService } from 'app/entities/system/system.service';
import { System } from 'app/shared/model/system.model';

describe('Component Tests', () => {
  describe('System Management Update Component', () => {
    let comp: SystemUpdateComponent;
    let fixture: ComponentFixture<SystemUpdateComponent>;
    let service: SystemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [SystemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SystemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SystemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new System(123);
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
        const entity = new System();
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
