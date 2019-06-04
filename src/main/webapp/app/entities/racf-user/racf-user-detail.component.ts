import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRacfUser } from 'app/shared/model/racf-user.model';

@Component({
  selector: 'jhi-racf-user-detail',
  templateUrl: './racf-user-detail.component.html'
})
export class RacfUserDetailComponent implements OnInit {
  racfUser: IRacfUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ racfUser }) => {
      this.racfUser = racfUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
