export const enum IpVersion {
  IPV4 = 'IPV4',
  IPV6 = 'IPV6'
}

export interface IArmIp {
  id?: number;
  ip?: string;
  ipVersion?: IpVersion;
  armId?: number;
}

export class ArmIp implements IArmIp {
  constructor(public id?: number, public ip?: string, public ipVersion?: IpVersion, public armId?: number) {}
}
