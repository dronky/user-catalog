/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmUpdateComponent } from 'app/entities/arm/arm-update.component';
import { ArmService } from 'app/entities/arm/arm.service';
import { Arm } from 'app/shared/model/arm.model';

describe('Component Tests', () => {
  describe('Arm Management Update Component', () => {
    let comp: ArmUpdateComponent;
    let fixture: ComponentFixture<ArmUpdateComponent>;
    let service: ArmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Arm(123);
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
        const entity = new Arm();
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
