import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './reservation-terrain.reducer';

export const ReservationTerrain = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const reservationTerrainList = useAppSelector(state => state.reservationTerrain.entities);
  const loading = useAppSelector(state => state.reservationTerrain.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="reservation-terrain-heading" data-cy="ReservationTerrainHeading">
        <Translate contentKey="appApp.reservationTerrain.home.title">Reservation Terrains</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="appApp.reservationTerrain.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/reservation-terrain/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="appApp.reservationTerrain.home.createLabel">Create new Reservation Terrain</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reservationTerrainList && reservationTerrainList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="appApp.reservationTerrain.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('heure')}>
                  <Translate contentKey="appApp.reservationTerrain.heure">Heure</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('heure')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="appApp.reservationTerrain.date">Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th>
                  <Translate contentKey="appApp.reservationTerrain.reservation">Reservation</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="appApp.reservationTerrain.nomTerrain">Nom Terrain</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reservationTerrainList.map((reservationTerrain, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reservation-terrain/${reservationTerrain.id}`} color="link" size="sm">
                      {reservationTerrain.id}
                    </Button>
                  </td>
                  <td>{reservationTerrain.heure}</td>
                  <td>
                    {reservationTerrain.date ? <TextFormat type="date" value={reservationTerrain.date} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {reservationTerrain.reservation ? (
                      <Link to={`/reservation/${reservationTerrain.reservation.id}`}>{reservationTerrain.reservation.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reservationTerrain.nomTerrain ? (
                      <Link to={`/terrain/${reservationTerrain.nomTerrain.id}`}>{reservationTerrain.nomTerrain.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/reservation-terrain/${reservationTerrain.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/reservation-terrain/${reservationTerrain.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/reservation-terrain/${reservationTerrain.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="appApp.reservationTerrain.home.notFound">No Reservation Terrains found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReservationTerrain;
