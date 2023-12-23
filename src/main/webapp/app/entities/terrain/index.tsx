import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Terrain from './terrain';
import TerrainDetail from './terrain-detail';
import TerrainUpdate from './terrain-update';
import TerrainDeleteDialog from './terrain-delete-dialog';

const TerrainRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Terrain />} />
    <Route path="new" element={<TerrainUpdate />} />
    <Route path=":id">
      <Route index element={<TerrainDetail />} />
      <Route path="edit" element={<TerrainUpdate />} />
      <Route path="delete" element={<TerrainDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TerrainRoutes;
