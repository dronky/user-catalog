import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISystem, System } from 'app/shared/model/system.model';
import { SystemService } from './system.service';
import { IRacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from 'app/entities/racf-user';

@Component({
  selector: 'jhi-system-update',
  templateUrl: './system-update.component.html'
})
export class SystemUpdateComponent implements OnInit {
  system: ISystem;
  isSaving: boolean;

  racfusers: IRacfUser[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    ip: [null, [Validators.maxLength(39)]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected systemService: SystemService,
    protected racfUserService: RacfUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ system }) => {
      this.updateForm(system);
      this.system = system;
    });
    this.racfUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfUser[]>) => response.body)
      )
      .subscribe((res: IRacfUser[]) => (this.racfusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(system: ISystem) {
    this.editForm.patchValue({
      id: system.id,
      name: system.name,
      ip: system.ip
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const system = this.createFromForm();
    if (system.id !== undefined) {
      this.subscribeToSaveResponse(this.systemService.update(system));
    } else {
      this.subscribeToSaveResponse(this.systemService.create(system));
    }
  }

  private createFromForm(): ISystem {
    const entity = {
      ...new System(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      ip: this.editForm.get(['ip']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISystem>>) {
    result.subscribe((res: HttpResponse<ISystem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
