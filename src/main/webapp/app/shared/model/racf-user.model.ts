import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { ISystem } from 'app/shared/model/system.model';
import { IRequest } from 'app/shared/model/request.model';

export interface IRacfUser {
  id?: number;
  name?: string;
  uid?: number;
  type?: string;
  ownerId?: number;
  armName?: string;
  armId?: number;
  groups?: IRacfGroup[];
  systems?: ISystem[];
  names?: IRequest[];
}

export class RacfUser implements IRacfUser {
  constructor(
    public id?: number,
    public name?: string,
    public uid?: number,
    public type?: string,
    public ownerId?: number,
    public armName?: string,
    public armId?: number,
    public groups?: IRacfGroup[],
    public systems?: ISystem[],
    public names?: IRequest[]
  ) {}
}
