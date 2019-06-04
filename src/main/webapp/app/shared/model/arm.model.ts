import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IArm {
  id?: number;
  name?: string;
  prodIp?: string;
  extraProdIp?: string;
  testIp?: string;
  extraTestIp?: string;
  names?: IRacfUser[];
}

export class Arm implements IArm {
  constructor(
    public id?: number,
    public name?: string,
    public prodIp?: string,
    public extraProdIp?: string,
    public testIp?: string,
    public extraTestIp?: string,
    public names?: IRacfUser[]
  ) {}
}
