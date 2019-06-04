import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IRacfGroup {
  id?: number;
  name?: string;
  gid?: number;
  names?: IRacfUser[];
}

export class RacfGroup implements IRacfGroup {
  constructor(public id?: number, public name?: string, public gid?: number, public names?: IRacfUser[]) {}
}
