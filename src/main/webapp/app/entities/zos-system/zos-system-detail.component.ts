import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZosSystem } from 'app/shared/model/zos-system.model';

@Component({
  selector: 'jhi-zos-system-detail',
  templateUrl: './zos-system-detail.component.html'
})
export class ZosSystemDetailComponent implements OnInit {
  zosSystem: IZosSystem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ zosSystem }) => {
      this.zosSystem = zosSystem;
    });
  }

  previousState() {
    window.history.back();
  }
}
