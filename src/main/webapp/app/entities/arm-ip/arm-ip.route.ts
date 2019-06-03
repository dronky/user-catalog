import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ArmIp } from 'app/shared/model/arm-ip.model';
import { ArmIpService } from './arm-ip.service';
import { ArmIpComponent } from './arm-ip.component';
import { ArmIpDetailComponent } from './arm-ip-detail.component';
import { ArmIpUpdateComponent } from './arm-ip-update.component';
import { ArmIpDeletePopupComponent } from './arm-ip-delete-dialog.component';
import { IArmIp } from 'app/shared/model/arm-ip.model';

@Injectable({ providedIn: 'root' })
export class ArmIpResolve implements Resolve<IArmIp> {
  constructor(private service: ArmIpService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IArmIp> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ArmIp>) => response.ok),
        map((armIp: HttpResponse<ArmIp>) => armIp.body)
      );
    }
    return of(new ArmIp());
  }
}

export const armIpRoute: Routes = [
  {
    path: '',
    component: ArmIpComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.armIp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArmIpDetailComponent,
    resolve: {
      armIp: ArmIpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.armIp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArmIpUpdateComponent,
    resolve: {
      armIp: ArmIpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.armIp.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArmIpUpdateComponent,
    resolve: {
      armIp: ArmIpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.armIp.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const armIpPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ArmIpDeletePopupComponent,
    resolve: {
      armIp: ArmIpResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'userCatalogApp.armIp.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
