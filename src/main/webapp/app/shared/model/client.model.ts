import { IPackClient } from 'app/shared/model/pack-client.model';
import { IReservation } from 'app/shared/model/reservation.model';

export interface IClient {
  id?: number;
  nomClient?: string | null;
  prenom?: string | null;
  email?: string | null;
  password?: string | null;
  packClients?: IPackClient[] | null;
  reservations?: IReservation[] | null;
}

export const defaultValue: Readonly<IClient> = {};
