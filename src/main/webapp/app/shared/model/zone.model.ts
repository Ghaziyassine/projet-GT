import { ITerrain } from 'app/shared/model/terrain.model';
import { IVille } from 'app/shared/model/ville.model';

export interface IZone {
  id?: number;
  nomZone?: string | null;
  terrains?: ITerrain[] | null;
  nomVille?: IVille | null;
}

export const defaultValue: Readonly<IZone> = {};
