import { IZone } from 'app/shared/model/zone.model';

export interface IVille {
  id?: number;
  nomVille?: string | null;
  zones?: IZone[] | null;
}

export const defaultValue: Readonly<IVille> = {};
