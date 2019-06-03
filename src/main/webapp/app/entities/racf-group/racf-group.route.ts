import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RacfGroup } from 'app/shared/model/racf-group.model';
import { RacfGroupService } from './racf-group.service';
import { RacfGroupComponent } from './racf-group.component';
import { RacfGroupDetailComponent } from './racf-group-detail.component';
import { RacfGroupUpdateComponent } from './racf-group-update.component';
import { RacfGroupDeletePopupComponent } from './racf-group-delete-dialog.component';
import { IRacfGroup } from 'app/shared/model/racf-group.model';

@Injectable({ providedIn: 'root' })
export class RacfGroupResolve implements Resolve<IRacfGroup> {
  constructor(private service: RacfGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRacfGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RacfGroup>) => response.ok),
        map((racfGroup: HttpResponse<RacfGroup>) => racfGroup.body)
      );
    }
    return of(new RacfGroup());
  }
}

export const racfGroupRoute: Routes = [
  {
    path: '',
    component: RacfGroupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.racfGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RacfGroupDetailComponent,
    resolve: {
      racfGroup: RacfGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RacfGroupUpdateComponent,
    resolve: {
      racfGroup: RacfGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RacfGroupUpdateComponent,
    resolve: {
      racfGroup: RacfGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const racfGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RacfGroupDeletePopupComponent,
    resolve: {
      racfGroup: RacfGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.racfGroup.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
