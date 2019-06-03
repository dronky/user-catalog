/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmIpUpdateComponent } from 'app/entities/arm-ip/arm-ip-update.component';
import { ArmIpService } from 'app/entities/arm-ip/arm-ip.service';
import { ArmIp } from 'app/shared/model/arm-ip.model';

describe('Component Tests', () => {
  describe('ArmIp Management Update Component', () => {
    let comp: ArmIpUpdateComponent;
    let fixture: ComponentFixture<ArmIpUpdateComponent>;
    let service: ArmIpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmIpUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArmIpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmIpUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmIpService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ArmIp(123);
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
        const entity = new ArmIp();
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
