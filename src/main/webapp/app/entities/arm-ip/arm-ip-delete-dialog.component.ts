import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IArmIp } from 'app/shared/model/arm-ip.model';
import { ArmIpService } from './arm-ip.service';

@Component({
  selector: 'jhi-arm-ip-delete-dialog',
  templateUrl: './arm-ip-delete-dialog.component.html'
})
export class ArmIpDeleteDialogComponent {
  armIp: IArmIp;

  constructor(protected armIpService: ArmIpService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.armIpService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'armIpListModification',
        content: 'Deleted an armIp'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-arm-ip-delete-popup',
  template: ''
})
export class ArmIpDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ armIp }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ArmIpDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.armIp = armIp;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/arm-ip', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/arm-ip', { outlets: { popup: null } }]);
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
