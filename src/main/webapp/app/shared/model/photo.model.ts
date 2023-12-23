import { ITerrain } from 'app/shared/model/terrain.model';

export interface IPhoto {
  id?: number;
  photoContentType?: string | null;
  photo?: string | null;
  nomTerrain?: ITerrain | null;
}

export const defaultValue: Readonly<IPhoto> = {};
