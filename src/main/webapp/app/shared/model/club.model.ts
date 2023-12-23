import { ITerrain } from 'app/shared/model/terrain.model';

export interface IClub {
  id?: number;
  nomClub?: string | null;
  terrains?: ITerrain[] | null;
}

export const defaultValue: Readonly<IClub> = {};
