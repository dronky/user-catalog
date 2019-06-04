import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArm } from 'app/shared/model/arm.model';
import { ArmService } from './arm.service';

@Component({
  selector: 'jhi-arm-delete-dialog',
  templateUrl: './arm-delete-dialog.component.html'
})
export class ArmDeleteDialogComponent {
  arm: IArm;

  constructor(protected armService: ArmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.armService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'armListModification',
        content: 'Deleted an arm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-arm-delete-popup',
  template: ''
})
export class ArmDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ arm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ArmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.arm = arm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/arm', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/arm', { outlets: { popup: null } }]);
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
