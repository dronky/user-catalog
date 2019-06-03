import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Arm } from 'app/shared/model/arm.model';
import { ArmService } from './arm.service';
import { ArmComponent } from './arm.component';
import { ArmDetailComponent } from './arm-detail.component';
import { ArmUpdateComponent } from './arm-update.component';
import { ArmDeletePopupComponent } from './arm-delete-dialog.component';
import { IArm } from 'app/shared/model/arm.model';

@Injectable({ providedIn: 'root' })
export class ArmResolve implements Resolve<IArm> {
  constructor(private service: ArmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IArm> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Arm>) => response.ok),
        map((arm: HttpResponse<Arm>) => arm.body)
      );
    }
    return of(new Arm());
  }
}

export const armRoute: Routes = [
  {
    path: '',
    component: ArmComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'userCatalogApp.arm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArmDetailComponent,
    resolve: {
      arm: ArmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.arm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArmUpdateComponent,
    resolve: {
      arm: ArmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.arm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArmUpdateComponent,
    resolve: {
      arm: ArmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.arm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const armPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ArmDeletePopupComponent,
    resolve: {
      arm: ArmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.arm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
