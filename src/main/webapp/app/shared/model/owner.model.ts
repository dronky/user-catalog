export interface IOwner {
  id?: number;
  name?: string;
  phone?: string;
  racfUserId?: number;
}

export class Owner implements IOwner {
  constructor(public id?: number, public name?: string, public phone?: string, public racfUserId?: number) {}
}
