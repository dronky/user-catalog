import { IRacfUser } from 'app/shared/model/racf-user.model';

export interface IArm {
  id?: number;
  name?: string;
  prodIp?: string;
  testIp?: string;
  additionalIp?: string;
  names?: IRacfUser[];
}

export class Arm implements IArm {
  constructor(
    public id?: number,
    public name?: string,
    public prodIp?: string,
    public testIp?: string,
    public additionalIp?: string,
    public names?: IRacfUser[]
  ) {}
}
