import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystem } from 'app/shared/model/system.model';
import { SystemService } from './system.service';

@Component({
  selector: 'jhi-system-delete-dialog',
  templateUrl: './system-delete-dialog.component.html'
})
export class SystemDeleteDialogComponent {
  system: ISystem;

  constructor(protected systemService: SystemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.systemService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'systemListModification',
        content: 'Deleted an system'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-system-delete-popup',
  template: ''
})
export class SystemDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ system }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SystemDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.system = system;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/system', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/system', { outlets: { popup: null } }]);
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
