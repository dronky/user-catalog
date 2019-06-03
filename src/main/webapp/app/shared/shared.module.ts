import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UserCatalogSharedLibsModule, UserCatalogSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [UserCatalogSharedLibsModule, UserCatalogSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [UserCatalogSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogSharedModule {
  static forRoot() {
    return {
      ngModule: UserCatalogSharedModule
    };
  }
}
