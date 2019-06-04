import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { UserCatalogSharedModule } from 'app/shared';
import {
  ZosSystemComponent,
  ZosSystemDetailComponent,
  ZosSystemUpdateComponent,
  ZosSystemDeletePopupComponent,
  ZosSystemDeleteDialogComponent,
  zosSystemRoute,
  zosSystemPopupRoute
} from './';

const ENTITY_STATES = [...zosSystemRoute, ...zosSystemPopupRoute];

@NgModule({
  imports: [UserCatalogSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ZosSystemComponent,
    ZosSystemDetailComponent,
    ZosSystemUpdateComponent,
    ZosSystemDeleteDialogComponent,
    ZosSystemDeletePopupComponent
  ],
  entryComponents: [ZosSystemComponent, ZosSystemUpdateComponent, ZosSystemDeleteDialogComponent, ZosSystemDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogZosSystemModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
