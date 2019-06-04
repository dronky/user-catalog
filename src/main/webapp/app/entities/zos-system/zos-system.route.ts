import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ZosSystem } from 'app/shared/model/zos-system.model';
import { ZosSystemService } from './zos-system.service';
import { ZosSystemComponent } from './zos-system.component';
import { ZosSystemDetailComponent } from './zos-system-detail.component';
import { ZosSystemUpdateComponent } from './zos-system-update.component';
import { ZosSystemDeletePopupComponent } from './zos-system-delete-dialog.component';
import { IZosSystem } from 'app/shared/model/zos-system.model';

@Injectable({ providedIn: 'root' })
export class ZosSystemResolve implements Resolve<IZosSystem> {
  constructor(private service: ZosSystemService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IZosSystem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ZosSystem>) => response.ok),
        map((zosSystem: HttpResponse<ZosSystem>) => zosSystem.body)
      );
    }
    return of(new ZosSystem());
  }
}

export const zosSystemRoute: Routes = [
  {
    path: '',
    component: ZosSystemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.zosSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ZosSystemDetailComponent,
    resolve: {
      zosSystem: ZosSystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.zosSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ZosSystemUpdateComponent,
    resolve: {
      zosSystem: ZosSystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.zosSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ZosSystemUpdateComponent,
    resolve: {
      zosSystem: ZosSystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.zosSystem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const zosSystemPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ZosSystemDeletePopupComponent,
    resolve: {
      zosSystem: ZosSystemResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.zosSystem.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
