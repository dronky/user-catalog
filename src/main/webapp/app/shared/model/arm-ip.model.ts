import { IArm } from 'app/shared/model/arm.model';

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
  arms?: IArm[];
}

export class ArmIp implements IArmIp {
  constructor(public id?: number, public ip?: string, public type?: Type, public ipVersion?: IpVersion, public arms?: IArm[]) {}
}
