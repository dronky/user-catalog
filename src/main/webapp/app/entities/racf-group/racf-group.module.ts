import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  RacfGroupComponent,
  RacfGroupDetailComponent,
  RacfGroupUpdateComponent,
  RacfGroupDeletePopupComponent,
  RacfGroupDeleteDialogComponent,
  racfGroupRoute,
  racfGroupPopupRoute
} from './';

const ENTITY_STATES = [...racfGroupRoute, ...racfGroupPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RacfGroupComponent,
    RacfGroupDetailComponent,
    RacfGroupUpdateComponent,
    RacfGroupDeleteDialogComponent,
    RacfGroupDeletePopupComponent
  ],
  entryComponents: [RacfGroupComponent, RacfGroupUpdateComponent, RacfGroupDeleteDialogComponent, RacfGroupDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogRacfGroupModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
