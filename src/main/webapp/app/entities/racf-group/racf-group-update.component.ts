import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRacfGroup, RacfGroup } from 'app/shared/model/racf-group.model';
import { RacfGroupService } from './racf-group.service';
import { IRacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from 'app/entities/racf-user';

@Component({
  selector: 'jhi-racf-group-update',
  templateUrl: './racf-group-update.component.html'
})
export class RacfGroupUpdateComponent implements OnInit {
  racfGroup: IRacfGroup;
  isSaving: boolean;

  racfusers: IRacfUser[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(8)]],
    gid: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected racfGroupService: RacfGroupService,
    protected racfUserService: RacfUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ racfGroup }) => {
      this.updateForm(racfGroup);
      this.racfGroup = racfGroup;
    });
    this.racfUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfUser[]>) => response.body)
      )
      .subscribe((res: IRacfUser[]) => (this.racfusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(racfGroup: IRacfGroup) {
    this.editForm.patchValue({
      id: racfGroup.id,
      name: racfGroup.name,
      gid: racfGroup.gid
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const racfGroup = this.createFromForm();
    if (racfGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.racfGroupService.update(racfGroup));
    } else {
      this.subscribeToSaveResponse(this.racfGroupService.create(racfGroup));
    }
  }

  private createFromForm(): IRacfGroup {
    const entity = {
      ...new RacfGroup(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      gid: this.editForm.get(['gid']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRacfGroup>>) {
    result.subscribe((res: HttpResponse<IRacfGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRacfUserById(index: number, item: IRacfUser) {
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
