/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmDeleteDialogComponent } from 'app/entities/arm/arm-delete-dialog.component';
import { ArmService } from 'app/entities/arm/arm.service';

describe('Component Tests', () => {
  describe('Arm Management Delete Component', () => {
    let comp: ArmDeleteDialogComponent;
    let fixture: ComponentFixture<ArmDeleteDialogComponent>;
    let service: ArmService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmDeleteDialogComponent]
      })
        .overrideTemplate(ArmDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArmDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
