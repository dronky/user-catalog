import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IArm, Arm } from 'app/shared/model/arm.model';
import { ArmService } from './arm.service';

@Component({
  selector: 'jhi-arm-update',
  templateUrl: './arm-update.component.html'
})
export class ArmUpdateComponent implements OnInit {
  arm: IArm;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    prodIp: [],
    testIp: [],
    additionalIp: []
  });

  constructor(protected armService: ArmService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ arm }) => {
      this.updateForm(arm);
      this.arm = arm;
    });
  }

  updateForm(arm: IArm) {
    this.editForm.patchValue({
      id: arm.id,
      name: arm.name,
      prodIp: arm.prodIp,
      testIp: arm.testIp,
      additionalIp: arm.additionalIp
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
      prodIp: this.editForm.get(['prodIp']).value,
      testIp: this.editForm.get(['testIp']).value,
      additionalIp: this.editForm.get(['additionalIp']).value
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
}
