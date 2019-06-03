import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { System } from 'app/shared/model/system.model';
import { SystemService } from './system.service';
import { SystemComponent } from './system.component';
import { SystemDetailComponent } from './system-detail.component';
import { SystemUpdateComponent } from './system-update.component';
import { SystemDeletePopupComponent } from './system-delete-dialog.component';
import { ISystem } from 'app/shared/model/system.model';

@Injectable({ providedIn: 'root' })
export class SystemResolve implements Resolve<ISystem> {
  constructor(private service: SystemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISystem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<System>) => response.ok),
        map((system: HttpResponse<System>) => system.body)
      );
    }
    return of(new System());
  }
}

export const systemRoute: Routes = [
  {
    path: '',
    component: SystemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.system.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SystemDetailComponent,
    resolve: {
      system: SystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.system.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SystemUpdateComponent,
    resolve: {
      system: SystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.system.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SystemUpdateComponent,
    resolve: {
      system: SystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.system.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const systemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SystemDeletePopupComponent,
    resolve: {
      system: SystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.system.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
