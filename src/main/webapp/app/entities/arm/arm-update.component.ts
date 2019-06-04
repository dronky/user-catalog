import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IArm, Arm } from 'app/shared/model/arm.model';
import { ArmService } from './arm.service';
import { IArmIp } from 'app/shared/model/arm-ip.model';
import { ArmIpService } from 'app/entities/arm-ip';

@Component({
  selector: 'jhi-arm-update',
  templateUrl: './arm-update.component.html'
})
export class ArmUpdateComponent implements OnInit {
  arm: IArm;
  isSaving: boolean;

  armips: IArmIp[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    armIps: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected armService: ArmService,
    protected armIpService: ArmIpService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ arm }) => {
      this.updateForm(arm);
      this.arm = arm;
    });
    this.armIpService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArmIp[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArmIp[]>) => response.body)
      )
      .subscribe((res: IArmIp[]) => (this.armips = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(arm: IArm) {
    this.editForm.patchValue({
      id: arm.id,
      name: arm.name,
      armIps: arm.armIps
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const arm = this.createFromForm();
    if (arm.id !== undefined) {
      this.subscribeToSaveResponse(this.armService.update(arm));
    } else {
      this.subscribeToSaveResponse(this.armService.create(arm));
    }
  }

  private createFromForm(): IArm {
    const entity = {
      ...new Arm(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      armIps: this.editForm.get(['armIps']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArm>>) {
    result.subscribe((res: HttpResponse<IArm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackArmIpById(index: number, item: IArmIp) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
