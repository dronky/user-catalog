import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  RacfUserComponent,
  RacfUserDetailComponent,
  RacfUserUpdateComponent,
  RacfUserDeletePopupComponent,
  RacfUserDeleteDialogComponent,
  racfUserRoute,
  racfUserPopupRoute
} from './';

const ENTITY_STATES = [...racfUserRoute, ...racfUserPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RacfUserComponent,
    RacfUserDetailComponent,
    RacfUserUpdateComponent,
    RacfUserDeleteDialogComponent,
    RacfUserDeletePopupComponent
  ],
  entryComponents: [RacfUserComponent, RacfUserUpdateComponent, RacfUserDeleteDialogComponent, RacfUserDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogRacfUserModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
