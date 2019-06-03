import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IArmIp, ArmIp } from 'app/shared/model/arm-ip.model';
import { ArmIpService } from './arm-ip.service';
import { IArm } from 'app/shared/model/arm.model';
import { ArmService } from 'app/entities/arm';

@Component({
  selector: 'jhi-arm-ip-update',
  templateUrl: './arm-ip-update.component.html'
})
export class ArmIpUpdateComponent implements OnInit {
  armIp: IArmIp;
  isSaving: boolean;

  arms: IArm[];

  editForm = this.fb.group({
    id: [],
    ip: [null, [Validators.required]],
    type: [],
    ipVersion: [],
    armId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected armIpService: ArmIpService,
    protected armService: ArmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ armIp }) => {
      this.updateForm(armIp);
      this.armIp = armIp;
    });
    this.armService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArm[]>) => response.body)
      )
      .subscribe((res: IArm[]) => (this.arms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(armIp: IArmIp) {
    this.editForm.patchValue({
      id: armIp.id,
      ip: armIp.ip,
      type: armIp.type,
      ipVersion: armIp.ipVersion,
      armId: armIp.armId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const armIp = this.createFromForm();
    if (armIp.id !== undefined) {
      this.subscribeToSaveResponse(this.armIpService.update(armIp));
    } else {
      this.subscribeToSaveResponse(this.armIpService.create(armIp));
    }
  }

  private createFromForm(): IArmIp {
    const entity = {
      ...new ArmIp(),
      id: this.editForm.get(['id']).value,
      ip: this.editForm.get(['ip']).value,
      type: this.editForm.get(['type']).value,
      ipVersion: this.editForm.get(['ipVersion']).value,
      armId: this.editForm.get(['armId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArmIp>>) {
    result.subscribe((res: HttpResponse<IArmIp>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackArmById(index: number, item: IArm) {
    return item.id;
  }
}
