import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RacfUser } from 'app/shared/model/racf-user.model';
import { RacfUserService } from './racf-user.service';
import { RacfUserComponent } from './racf-user.component';
import { RacfUserDetailComponent } from './racf-user-detail.component';
import { RacfUserUpdateComponent } from './racf-user-update.component';
import { RacfUserDeletePopupComponent } from './racf-user-delete-dialog.component';
import { IRacfUser } from 'app/shared/model/racf-user.model';

@Injectable({ providedIn: 'root' })
export class RacfUserResolve implements Resolve<IRacfUser> {
  constructor(private service: RacfUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRacfUser> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RacfUser>) => response.ok),
        map((racfUser: HttpResponse<RacfUser>) => racfUser.body)
      );
    }
    return of(new RacfUser());
  }
}

export const racfUserRoute: Routes = [
  {
    path: '',
    component: RacfUserComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.racfUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RacfUserDetailComponent,
    resolve: {
      racfUser: RacfUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RacfUserUpdateComponent,
    resolve: {
      racfUser: RacfUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RacfUserUpdateComponent,
    resolve: {
      racfUser: RacfUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const racfUserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RacfUserDeletePopupComponent,
    resolve: {
      racfUser: RacfUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
