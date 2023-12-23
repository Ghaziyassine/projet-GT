import dayjs from 'dayjs';
import { IReservationTerrain } from 'app/shared/model/reservation-terrain.model';
import { IClient } from 'app/shared/model/client.model';

export interface IReservation {
  id?: number;
  date?: dayjs.Dayjs | null;
  etat?: string | null;
  reservationTerrains?: IReservationTerrain[] | null;
  nomClient?: IClient | null;
}

export const defaultValue: Readonly<IReservation> = {};
