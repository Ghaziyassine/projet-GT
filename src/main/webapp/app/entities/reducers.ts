import client from 'app/entities/client/client.reducer';
import club from 'app/entities/club/club.reducer';
import pack from 'app/entities/pack/pack.reducer';
import packClient from 'app/entities/pack-client/pack-client.reducer';
import photo from 'app/entities/photo/photo.reducer';
import reservation from 'app/entities/reservation/reservation.reducer';
import reservationTerrain from 'app/entities/reservation-terrain/reservation-terrain.reducer';
import terrain from 'app/entities/terrain/terrain.reducer';
import ville from 'app/entities/ville/ville.reducer';
import zone from 'app/entities/zone/zone.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  client,
  club,
  pack,
  packClient,
  photo,
  reservation,
  reservationTerrain,
  terrain,
  ville,
  zone,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
