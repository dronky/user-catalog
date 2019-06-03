export interface IRequest {
  id?: number;
  esppRequest?: string;
  asozRequest?: string;
  racfUserId?: number;
}

export class Request implements IRequest {
  constructor(public id?: number, public esppRequest?: string, public asozRequest?: string, public racfUserId?: number) {}
}
