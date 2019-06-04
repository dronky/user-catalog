import { IRacfUser } from 'app/shared/model/racf-user.model';
import { IArmIp } from 'app/shared/model/arm-ip.model';

export interface IArm {
  id?: number;
  name?: string;
  names?: IRacfUser[];
  armIps?: IArmIp[];
}

export class Arm implements IArm {
  constructor(public id?: number, public name?: string, public names?: IRacfUser[], public armIps?: IArmIp[]) {}
}
