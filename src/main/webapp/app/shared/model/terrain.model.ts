import { IPhoto } from 'app/shared/model/photo.model';
import { IReservationTerrain } from 'app/shared/model/reservation-terrain.model';
import { IClub } from 'app/shared/model/club.model';
import { IZone } from 'app/shared/model/zone.model';

export interface ITerrain {
  id?: number;
  nomTerrain?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  adresse?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  rank?: number | null;
  type?: string | null;
  etat?: string | null;
  description?: string | null;
  typeSal?: string | null;
  tarif?: string | null;
  photos?: IPhoto[] | null;
  reservationTerrains?: IReservationTerrain[] | null;
  nomClub?: IClub | null;
  nomZone?: IZone | null;
}

export const defaultValue: Readonly<ITerrain> = {};
