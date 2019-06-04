import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IOwner {
  id?: number;
  name?: string;
  phone?: string;
  racfUsers?: IRacfUser[];
}

export class Owner implements IOwner {
  constructor(public id?: number, public name?: string, public phone?: string, public racfUsers?: IRacfUser[]) {}
}
