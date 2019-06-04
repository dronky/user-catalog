import { IArmIp } from 'app/shared/model/arm-ip.model';
import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IArm {
  id?: number;
  name?: string;
  armIps?: IArmIp[];
  names?: IRacfUser[];
  armIps?: IArmIp[];
}

export class Arm implements IArm {
  constructor(public id?: number, public name?: string, public armIps?: IArmIp[], public names?: IRacfUser[], public armIps?: IArmIp[]) {}
}
