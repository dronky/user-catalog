import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'racf-user',
        loadChildren: './racf-user/racf-user.module#UserCatalogRacfUserModule'
      },
      {
        path: 'racf-group',
        loadChildren: './racf-group/racf-group.module#UserCatalogRacfGroupModule'
      },
      {
        path: 'arm',
        loadChildren: './arm/arm.module#UserCatalogArmModule'
      },
      {
        path: 'zos-system',
        loadChildren: './zos-system/zos-system.module#UserCatalogZosSystemModule'
      },
      {
        path: 'owner',
        loadChildren: './owner/owner.module#UserCatalogOwnerModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class UserCatalogEntityModule {}
