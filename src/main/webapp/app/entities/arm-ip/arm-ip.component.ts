import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IArmIp } from 'app/shared/model/arm-ip.model';
import { AccountService } from 'app/core';
import { ArmIpService } from './arm-ip.service';

@Component({
  selector: 'jhi-arm-ip',
  templateUrl: './arm-ip.component.html'
})
export class ArmIpComponent implements OnInit, OnDestroy {
  armIps: IArmIp[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected armIpService: ArmIpService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.armIpService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IArmIp[]>) => res.ok),
          map((res: HttpResponse<IArmIp[]>) => res.body)
        )
        .subscribe((res: IArmIp[]) => (this.armIps = res), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.armIpService
      .query()
      .pipe(
        filter((res: HttpResponse<IArmIp[]>) => res.ok),
        map((res: HttpResponse<IArmIp[]>) => res.body)
      )
      .subscribe(
        (res: IArmIp[]) => {
          this.armIps = res;
          this.currentSearch = '';
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInArmIps();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IArmIp) {
    return item.id;
  }

  registerChangeInArmIps() {
    this.eventSubscriber = this.eventManager.subscribe('armIpListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
