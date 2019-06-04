import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IArm } from 'app/shared/model/arm.model';

@Component({
  selector: 'jhi-arm-detail',
  templateUrl: './arm-detail.component.html'
})
export class ArmDetailComponent implements OnInit {
  arm: IArm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ arm }) => {
      this.arm = arm;
    });
  }

  previousState() {
    window.history.back();
  }
}
