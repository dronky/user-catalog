import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRacfGroup } from 'app/shared/model/racf-group.model';

@Component({
  selector: 'jhi-racf-group-detail',
  templateUrl: './racf-group-detail.component.html'
})
export class RacfGroupDetailComponent implements OnInit {
  racfGroup: IRacfGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ racfGroup }) => {
      this.racfGroup = racfGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
