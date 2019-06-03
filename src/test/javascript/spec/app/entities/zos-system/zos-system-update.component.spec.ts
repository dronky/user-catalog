/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ZosSystemUpdateComponent } from 'app/entities/zos-system/zos-system-update.component';
import { ZosSystemService } from 'app/entities/zos-system/zos-system.service';
import { ZosSystem } from 'app/shared/model/zos-system.model';

describe('Component Tests', () => {
  describe('ZosSystem Management Update Component', () => {
    let comp: ZosSystemUpdateComponent;
    let fixture: ComponentFixture<ZosSystemUpdateComponent>;
    let service: ZosSystemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ZosSystemUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ZosSystemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZosSystemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZosSystemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ZosSystem(123);
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
        const entity = new ZosSystem();
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
