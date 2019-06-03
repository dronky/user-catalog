import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOwner, Owner } from 'app/shared/model/owner.model';
import { OwnerService } from './owner.service';
import { IRacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from 'app/entities/racf-user';

@Component({
  selector: 'jhi-owner-update',
  templateUrl: './owner-update.component.html'
})
export class OwnerUpdateComponent implements OnInit {
  owner: IOwner;
  isSaving: boolean;

  racfusers: IRacfUser[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    familyName: [],
    patronymic: [],
    phone: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ownerService: OwnerService,
    protected racfUserService: RacfUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ owner }) => {
      this.updateForm(owner);
      this.owner = owner;
    });
    this.racfUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfUser[]>) => response.body)
      )
      .subscribe((res: IRacfUser[]) => (this.racfusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(owner: IOwner) {
    this.editForm.patchValue({
      id: owner.id,
      name: owner.name,
      familyName: owner.familyName,
      patronymic: owner.patronymic,
      phone: owner.phone
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const owner = this.createFromForm();
    if (owner.id !== undefined) {
      this.subscribeToSaveResponse(this.ownerService.update(owner));
    } else {
      this.subscribeToSaveResponse(this.ownerService.create(owner));
    }
  }

  private createFromForm(): IOwner {
    const entity = {
      ...new Owner(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      familyName: this.editForm.get(['familyName']).value,
      patronymic: this.editForm.get(['patronymic']).value,
      phone: this.editForm.get(['phone']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOwner>>) {
    result.subscribe((res: HttpResponse<IOwner>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
