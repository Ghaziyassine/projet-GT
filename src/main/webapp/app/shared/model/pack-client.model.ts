import dayjs from 'dayjs';
import { IClient } from 'app/shared/model/client.model';
import { IPack } from 'app/shared/model/pack.model';

export interface IPackClient {
  id?: number;
  date?: dayjs.Dayjs | null;
  nomClient?: IClient | null;
  nomPack?: IPack | null;
}

export const defaultValue: Readonly<IPackClient> = {};
