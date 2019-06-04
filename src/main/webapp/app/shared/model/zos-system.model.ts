import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IZosSystem {
  id?: number;
  name?: string;
  ip?: string;
  names?: IRacfUser[];
}

export class ZosSystem implements IZosSystem {
  constructor(public id?: number, public name?: string, public ip?: string, public names?: IRacfUser[]) {}
}
