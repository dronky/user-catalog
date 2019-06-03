import { IRequest } from 'app/shared/model/request.model';
import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { IZosSystem } from 'app/shared/model/zos-system.model';

export interface IRacfUser {
  id?: number;
  name?: string;
  uid?: number;
  type?: string;
  ownerName?: string;
  ownerId?: number;
  requests?: IRequest[];
  armName?: string;
  armId?: number;
  groups?: IRacfGroup[];
  systems?: IZosSystem[];
}

export class RacfUser implements IRacfUser {
  constructor(
    public id?: number,
    public name?: string,
    public uid?: number,
    public type?: string,
    public ownerName?: string,
    public ownerId?: number,
    public requests?: IRequest[],
    public armName?: string,
    public armId?: number,
    public groups?: IRacfGroup[],
    public systems?: IZosSystem[]
  ) {}
}
