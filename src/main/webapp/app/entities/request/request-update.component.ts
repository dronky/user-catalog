import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRequest, Request } from 'app/shared/model/request.model';
import { RequestService } from './request.service';
import { IRacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from 'app/entities/racf-user';

@Component({
  selector: 'jhi-request-update',
  templateUrl: './request-update.component.html'
})
export class RequestUpdateComponent implements OnInit {
  request: IRequest;
  isSaving: boolean;

  racfusers: IRacfUser[];

  editForm = this.fb.group({
    id: [],
    esppRequest: [],
    asozRequest: [],
    racfUserId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected requestService: RequestService,
    protected racfUserService: RacfUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ request }) => {
      this.updateForm(request);
      this.request = request;
    });
    this.racfUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRacfUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRacfUser[]>) => response.body)
      )
      .subscribe((res: IRacfUser[]) => (this.racfusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(request: IRequest) {
    this.editForm.patchValue({
      id: request.id,
      esppRequest: request.esppRequest,
      asozRequest: request.asozRequest,
      racfUserId: request.racfUserId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const request = this.createFromForm();
    if (request.id !== undefined) {
      this.subscribeToSaveResponse(this.requestService.update(request));
    } else {
      this.subscribeToSaveResponse(this.requestService.create(request));
    }
  }

  private createFromForm(): IRequest {
    const entity = {
      ...new Request(),
      id: this.editForm.get(['id']).value,
      esppRequest: this.editForm.get(['esppRequest']).value,
      asozRequest: this.editForm.get(['asozRequest']).value,
      racfUserId: this.editForm.get(['racfUserId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequest>>) {
    result.subscribe((res: HttpResponse<IRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
