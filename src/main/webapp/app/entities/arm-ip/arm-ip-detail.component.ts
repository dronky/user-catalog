import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArmIp } from 'app/shared/model/arm-ip.model';

@Component({
  selector: 'jhi-arm-ip-detail',
  templateUrl: './arm-ip-detail.component.html'
})
export class ArmIpDetailComponent implements OnInit {
  armIp: IArmIp;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ armIp }) => {
      this.armIp = armIp;
    });
  }

  previousState() {
    window.history.back();
  }
}
