/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UserCatalogTestModule } from '../../../test.module';
import { SystemDeleteDialogComponent } from 'app/entities/system/system-delete-dialog.component';
import { SystemService } from 'app/entities/system/system.service';

describe('Component Tests', () => {
  describe('System Management Delete Component', () => {
    let comp: SystemDeleteDialogComponent;
    let fixture: ComponentFixture<SystemDeleteDialogComponent>;
    let service: SystemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [SystemDeleteDialogComponent]
      })
        .overrideTemplate(SystemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SystemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SystemService);
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
