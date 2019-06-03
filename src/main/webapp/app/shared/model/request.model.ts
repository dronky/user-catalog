export interface IRequest {
  id?: number;
  esppRequest?: string;
  asozRequest?: string;
  racfUserName?: string;
  racfUserId?: number;
}

export class Request implements IRequest {
  constructor(
    public id?: number,
    public esppRequest?: string,
    public asozRequest?: string,
    public racfUserName?: string,
    public racfUserId?: number
  ) {}
}
