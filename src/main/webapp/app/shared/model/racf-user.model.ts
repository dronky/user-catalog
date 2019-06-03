import { IRequest } from 'app/shared/model/request.model';
import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { ISystem } from 'app/shared/model/system.model';

export interface IRacfUser {
  id?: number;
  name?: string;
  uid?: number;
  type?: string;
  ownerId?: number;
  requests?: IRequest[];
  armName?: string;
  armId?: number;
  groups?: IRacfGroup[];
  systems?: ISystem[];
}

export class RacfUser implements IRacfUser {
  constructor(
    public id?: number,
    public name?: string,
    public uid?: number,
    public type?: string,
    public ownerId?: number,
    public requests?: IRequest[],
    public armName?: string,
    public armId?: number,
    public groups?: IRacfGroup[],
    public systems?: ISystem[]
  ) {}
}
