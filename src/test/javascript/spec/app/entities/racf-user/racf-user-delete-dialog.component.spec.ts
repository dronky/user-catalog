/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { UserCatalogTestModule } from '../../../test.module';
import { RacfUserDeleteDialogComponent } from 'app/entities/racf-user/racf-user-delete-dialog.component';
import { RacfUserService } from 'app/entities/racf-user/racf-user.service';

describe('Component Tests', () => {
  describe('RacfUser Management Delete Component', () => {
    let comp: RacfUserDeleteDialogComponent;
    let fixture: ComponentFixture<RacfUserDeleteDialogComponent>;
    let service: RacfUserService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [RacfUserDeleteDialogComponent]
      })
        .overrideTemplate(RacfUserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RacfUserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RacfUserService);
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
