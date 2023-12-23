import dayjs from 'dayjs';
import { IReservation } from 'app/shared/model/reservation.model';
import { ITerrain } from 'app/shared/model/terrain.model';

export interface IReservationTerrain {
  id?: number;
  heure?: number | null;
  date?: dayjs.Dayjs | null;
  reservation?: IReservation | null;
  nomTerrain?: ITerrain | null;
}

export const defaultValue: Readonly<IReservationTerrain> = {};
