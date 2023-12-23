import { IPackClient } from 'app/shared/model/pack-client.model';

export interface IPack {
  id?: number;
  nomPack?: string | null;
  tarif?: string | null;
  nbrDeMatches?: number | null;
  packClients?: IPackClient[] | null;
}

export const defaultValue: Readonly<IPack> = {};
