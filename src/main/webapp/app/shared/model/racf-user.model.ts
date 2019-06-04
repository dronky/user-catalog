import { IRacfGroup } from 'app/shared/model/racf-group.model';
import { IZosSystem } from 'app/shared/model/zos-system.model';

export interface IRacfUser {
  id?: number;
  name?: string;
  uid?: number;
  type?: string;
  esppRequest?: string;
  asozRequest?: string;
  armName?: string;
  armId?: number;
  ownerName?: string;
  ownerId?: number;
  groups?: IRacfGroup[];
  systems?: IZosSystem[];
}

export class RacfUser implements IRacfUser {
  constructor(
    public id?: number,
    public name?: string,
    public uid?: number,
    public type?: string,
    public esppRequest?: string,
    public asozRequest?: string,
    public armName?: string,
    public armId?: number,
    public ownerName?: string,
    public ownerId?: number,
    public groups?: IRacfGroup[],
    public systems?: IZosSystem[]
  ) {}
}
