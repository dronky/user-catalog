import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Owner } from 'app/shared/model/owner.model';
import { OwnerService } from './owner.service';
import { OwnerComponent } from './owner.component';
import { OwnerDetailComponent } from './owner-detail.component';
import { OwnerUpdateComponent } from './owner-update.component';
import { OwnerDeletePopupComponent } from './owner-delete-dialog.component';
import { IOwner } from 'app/shared/model/owner.model';

@Injectable({ providedIn: 'root' })
export class OwnerResolve implements Resolve<IOwner> {
  constructor(private service: OwnerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOwner> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Owner>) => response.ok),
        map((owner: HttpResponse<Owner>) => owner.body)
      );
    }
    return of(new Owner());
  }
}

export const ownerRoute: Routes = [
  {
    path: '',
    component: OwnerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.owner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OwnerDetailComponent,
    resolve: {
      owner: OwnerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.owner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OwnerUpdateComponent,
    resolve: {
      owner: OwnerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.owner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OwnerUpdateComponent,
    resolve: {
      owner: OwnerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.owner.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ownerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OwnerDeletePopupComponent,
    resolve: {
      owner: OwnerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.owner.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
