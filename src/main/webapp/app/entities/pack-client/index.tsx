import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PackClient from './pack-client';
import PackClientDetail from './pack-client-detail';
import PackClientUpdate from './pack-client-update';
import PackClientDeleteDialog from './pack-client-delete-dialog';

const PackClientRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PackClient />} />
    <Route path="new" element={<PackClientUpdate />} />
    <Route path=":id">
      <Route index element={<PackClientDetail />} />
      <Route path="edit" element={<PackClientUpdate />} />
      <Route path="delete" element={<PackClientDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PackClientRoutes;
