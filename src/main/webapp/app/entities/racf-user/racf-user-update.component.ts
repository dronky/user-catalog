import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRacfUser, RacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from './racf-user.service';
import { IArm } from 'app/shared/model/arm.model';
import { ArmService } from 'app/entities/arm';
import { IOwner } from 'app/shared/model/owner.model';
import { OwnerService } from 'app/entities/owner';
import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { RacfGroupService } from 'app/entities/racf-group';
import { IZosSystem } from 'app/shared/model/zos-system.model';
import { ZosSystemService } from 'app/entities/zos-system';

@Component({
  selector: 'jhi-racf-user-update',
  templateUrl: './racf-user-update.component.html'
})
export class RacfUserUpdateComponent implements OnInit {
  racfUser: IRacfUser;
  isSaving: boolean;

  arms: IArm[];

  owners: IOwner[];

  racfgroups: IRacfGroup[];

  zossystems: IZosSystem[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(8)]],
    uid: [],
    type: [],
    esppRequest: [],
    asozRequest: [],
    armId: [],
    ownerId: [],
    groups: [],
    systems: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected racfUserService: RacfUserService,
    protected armService: ArmService,
    protected ownerService: OwnerService,
    protected racfGroupService: RacfGroupService,
    protected zosSystemService: ZosSystemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ racfUser }) => {
      this.updateForm(racfUser);
      this.racfUser = racfUser;
    });
    this.armService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArm[]>) => response.body)
      )
      .subscribe((res: IArm[]) => (this.arms = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ownerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOwner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOwner[]>) => response.body)
      )
      .subscribe((res: IOwner[]) => (this.owners = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.racfGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfGroup[]>) => response.body)
      )
      .subscribe((res: IRacfGroup[]) => (this.racfgroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.zosSystemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IZosSystem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IZosSystem[]>) => response.body)
      )
      .subscribe((res: IZosSystem[]) => (this.zossystems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(racfUser: IRacfUser) {
    this.editForm.patchValue({
      id: racfUser.id,
      name: racfUser.name,
      uid: racfUser.uid,
      type: racfUser.type,
      esppRequest: racfUser.esppRequest,
      asozRequest: racfUser.asozRequest,
      armId: racfUser.armId,
      ownerId: racfUser.ownerId,
      groups: racfUser.groups,
      systems: racfUser.systems
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const racfUser = this.createFromForm();
    if (racfUser.id !== undefined) {
      this.subscribeToSaveResponse(this.racfUserService.update(racfUser));
    } else {
      this.subscribeToSaveResponse(this.racfUserService.create(racfUser));
    }
  }

  private createFromForm(): IRacfUser {
    const entity = {
      ...new RacfUser(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      uid: this.editForm.get(['uid']).value,
      type: this.editForm.get(['type']).value,
      esppRequest: this.editForm.get(['esppRequest']).value,
      asozRequest: this.editForm.get(['asozRequest']).value,
      armId: this.editForm.get(['armId']).value,
      ownerId: this.editForm.get(['ownerId']).value,
      groups: this.editForm.get(['groups']).value,
      systems: this.editForm.get(['systems']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRacfUser>>) {
    result.subscribe((res: HttpResponse<IRacfUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOwnerById(index: number, item: IOwner) {
    return item.id;
  }

  trackRacfGroupById(index: number, item: IRacfGroup) {
    return item.id;
  }

  trackZosSystemById(index: number, item: IZosSystem) {
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
