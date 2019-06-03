import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface ISystem {
  id?: number;
  name?: string;
  ip?: string;
  names?: IRacfUser[];
}

export class System implements ISystem {
  constructor(public id?: number, public name?: string, public ip?: string, public names?: IRacfUser[]) {}
}
