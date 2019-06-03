import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZosSystem } from 'app/shared/model/zos-system.model';
import { ZosSystemService } from './zos-system.service';

@Component({
  selector: 'jhi-zos-system-delete-dialog',
  templateUrl: './zos-system-delete-dialog.component.html'
})
export class ZosSystemDeleteDialogComponent {
  zosSystem: IZosSystem;

  constructor(protected zosSystemService: ZosSystemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.zosSystemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'zosSystemListModification',
        content: 'Deleted an zosSystem'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-zos-system-delete-popup',
  template: ''
})
export class ZosSystemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zosSystem }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ZosSystemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.zosSystem = zosSystem;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/zos-system', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/zos-system', { outlets: { popup: null } }]);
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
