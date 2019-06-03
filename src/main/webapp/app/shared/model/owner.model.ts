export interface IOwner {
  id?: number;
  name?: string;
  familyName?: string;
  patronymic?: string;
  phone?: string;
  racfUserId?: number;
}

export class Owner implements IOwner {
  constructor(
    public id?: number,
    public name?: string,
    public familyName?: string,
    public patronymic?: string,
    public phone?: string,
    public racfUserId?: number
  ) {}
}
