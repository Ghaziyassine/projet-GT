import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Client from './client';
import Club from './club';
import Pack from './pack';
import PackClient from './pack-client';
import Photo from './photo';
import Reservation from './reservation';
import ReservationTerrain from './reservation-terrain';
import Terrain from './terrain';
import Ville from './ville';
import Zone from './zone';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="client/*" element={<Client />} />
        <Route path="club/*" element={<Club />} />
        <Route path="pack/*" element={<Pack />} />
        <Route path="pack-client/*" element={<PackClient />} />
        <Route path="photo/*" element={<Photo />} />
        <Route path="reservation/*" element={<Reservation />} />
        <Route path="reservation-terrain/*" element={<ReservationTerrain />} />
        <Route path="terrain/*" element={<Terrain />} />
        <Route path="ville/*" element={<Ville />} />
        <Route path="zone/*" element={<Zone />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
