import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  ArmComponent,
  ArmDetailComponent,
  ArmUpdateComponent,
  ArmDeletePopupComponent,
  ArmDeleteDialogComponent,
  armRoute,
  armPopupRoute
} from './';

const ENTITY_STATES = [...armRoute, ...armPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ArmComponent, ArmDetailComponent, ArmUpdateComponent, ArmDeleteDialogComponent, ArmDeletePopupComponent],
  entryComponents: [ArmComponent, ArmUpdateComponent, ArmDeleteDialogComponent, ArmDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogArmModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
