export const enum Type {
  TEST_DEV = 'TEST_DEV',
  PRODUCTION = 'PRODUCTION'
}

export const enum IpVersion {
  IPV4 = 'IPV4',
  IPV6 = 'IPV6'
}

export interface IArmIp {
  id?: number;
  ip?: string;
  type?: Type;
  ipVersion?: IpVersion;
  armId?: number;
  armId?: number;
}

export class ArmIp implements IArmIp {
  constructor(
    public id?: number,
    public ip?: string,
    public type?: Type,
    public ipVersion?: IpVersion,
    public armId?: number,
    public armId?: number
  ) {}
}
