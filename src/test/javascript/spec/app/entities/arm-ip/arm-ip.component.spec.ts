/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { UserCatalogTestModule } from '../../../test.module';
import { ArmIpComponent } from 'app/entities/arm-ip/arm-ip.component';
import { ArmIpService } from 'app/entities/arm-ip/arm-ip.service';
import { ArmIp } from 'app/shared/model/arm-ip.model';

describe('Component Tests', () => {
  describe('ArmIp Management Component', () => {
    let comp: ArmIpComponent;
    let fixture: ComponentFixture<ArmIpComponent>;
    let service: ArmIpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [UserCatalogTestModule],
        declarations: [ArmIpComponent],
        providers: []
      })
        .overrideTemplate(ArmIpComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArmIpComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArmIpService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ArmIp(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.armIps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
