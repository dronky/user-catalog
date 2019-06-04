import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IZosSystem, ZosSystem } from 'app/shared/model/zos-system.model';
import { ZosSystemService } from './zos-system.service';
import { IRacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from 'app/entities/racf-user';

@Component({
  selector: 'jhi-zos-system-update',
  templateUrl: './zos-system-update.component.html'
})
export class ZosSystemUpdateComponent implements OnInit {
  zosSystem: IZosSystem;
  isSaving: boolean;

  racfusers: IRacfUser[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    ip: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected zosSystemService: ZosSystemService,
    protected racfUserService: RacfUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ zosSystem }) => {
      this.updateForm(zosSystem);
      this.zosSystem = zosSystem;
    });
    this.racfUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfUser[]>) => response.body)
      )
      .subscribe((res: IRacfUser[]) => (this.racfusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(zosSystem: IZosSystem) {
    this.editForm.patchValue({
      id: zosSystem.id,
      name: zosSystem.name,
      ip: zosSystem.ip
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const zosSystem = this.createFromForm();
    if (zosSystem.id !== undefined) {
      this.subscribeToSaveResponse(this.zosSystemService.update(zosSystem));
    } else {
      this.subscribeToSaveResponse(this.zosSystemService.create(zosSystem));
    }
  }

  private createFromForm(): IZosSystem {
    const entity = {
      ...new ZosSystem(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      ip: this.editForm.get(['ip']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZosSystem>>) {
    result.subscribe((res: HttpResponse<IZosSystem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
