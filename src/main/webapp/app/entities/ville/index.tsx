import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ville from './ville';
import VilleDetail from './ville-detail';
import VilleUpdate from './ville-update';
import VilleDeleteDialog from './ville-delete-dialog';

const VilleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ville />} />
    <Route path="new" element={<VilleUpdate />} />
    <Route path=":id">
      <Route index element={<VilleDetail />} />
      <Route path="edit" element={<VilleUpdate />} />
      <Route path="delete" element={<VilleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VilleRoutes;
