/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { UserCatalogTestModule } from '../../../test.module';
import { RacfGroupUpdateComponent } from 'app/entities/racf-group/racf-group-update.component';
import { RacfGroupService } from 'app/entities/racf-group/racf-group.service';
import { RacfGroup } from 'app/shared/model/racf-group.model';

describe('Component Tests', () => {
  describe('RacfGroup Management Update Component', () => {
    let comp: RacfGroupUpdateComponent;
    let fixture: ComponentFixture<RacfGroupUpdateComponent>;
    let service: RacfGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [RacfGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RacfGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RacfGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RacfGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RacfGroup(123);
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
        const entity = new RacfGroup();
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
