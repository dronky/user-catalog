import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  ArmIpComponent,
  ArmIpDetailComponent,
  ArmIpUpdateComponent,
  ArmIpDeletePopupComponent,
  ArmIpDeleteDialogComponent,
  armIpRoute,
  armIpPopupRoute
} from './';

const ENTITY_STATES = [...armIpRoute, ...armIpPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ArmIpComponent, ArmIpDetailComponent, ArmIpUpdateComponent, ArmIpDeleteDialogComponent, ArmIpDeletePopupComponent],
  entryComponents: [ArmIpComponent, ArmIpUpdateComponent, ArmIpDeleteDialogComponent, ArmIpDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogArmIpModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
