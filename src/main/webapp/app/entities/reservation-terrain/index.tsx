import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReservationTerrain from './reservation-terrain';
import ReservationTerrainDetail from './reservation-terrain-detail';
import ReservationTerrainUpdate from './reservation-terrain-update';
import ReservationTerrainDeleteDialog from './reservation-terrain-delete-dialog';

const ReservationTerrainRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReservationTerrain />} />
    <Route path="new" element={<ReservationTerrainUpdate />} />
    <Route path=":id">
      <Route index element={<ReservationTerrainDetail />} />
      <Route path="edit" element={<ReservationTerrainUpdate />} />
      <Route path="delete" element={<ReservationTerrainDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReservationTerrainRoutes;
