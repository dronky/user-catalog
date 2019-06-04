import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { RacfGroupService } from './racf-group.service';

@Component({
  selector: 'jhi-racf-group-delete-dialog',
  templateUrl: './racf-group-delete-dialog.component.html'
})
export class RacfGroupDeleteDialogComponent {
  racfGroup: IRacfGroup;

  constructor(protected racfGroupService: RacfGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.racfGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'racfGroupListModification',
        content: 'Deleted an racfGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-racf-group-delete-popup',
  template: ''
})
export class RacfGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ racfGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RacfGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.racfGroup = racfGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/racf-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/racf-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
