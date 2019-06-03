import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  SystemComponent,
  SystemDetailComponent,
  SystemUpdateComponent,
  SystemDeletePopupComponent,
  SystemDeleteDialogComponent,
  systemRoute,
  systemPopupRoute
} from './';

const ENTITY_STATES = [...systemRoute, ...systemPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SystemComponent, SystemDetailComponent, SystemUpdateComponent, SystemDeleteDialogComponent, SystemDeletePopupComponent],
  entryComponents: [SystemComponent, SystemUpdateComponent, SystemDeleteDialogComponent, SystemDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogSystemModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
