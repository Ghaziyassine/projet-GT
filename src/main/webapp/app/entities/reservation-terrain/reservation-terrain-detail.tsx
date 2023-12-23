import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reservation-terrain.reducer';

export const ReservationTerrainDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reservationTerrainEntity = useAppSelector(state => state.reservationTerrain.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reservationTerrainDetailsHeading">
          <Translate contentKey="appApp.reservationTerrain.detail.title">ReservationTerrain</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reservationTerrainEntity.id}</dd>
          <dt>
            <span id="heure">
              <Translate contentKey="appApp.reservationTerrain.heure">Heure</Translate>
            </span>
          </dt>
          <dd>{reservationTerrainEntity.heure}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="appApp.reservationTerrain.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {reservationTerrainEntity.date ? (
              <TextFormat value={reservationTerrainEntity.date} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="appApp.reservationTerrain.reservation">Reservation</Translate>
          </dt>
          <dd>{reservationTerrainEntity.reservation ? reservationTerrainEntity.reservation.id : ''}</dd>
          <dt>
            <Translate contentKey="appApp.reservationTerrain.nomTerrain">Nom Terrain</Translate>
          </dt>
          <dd>{reservationTerrainEntity.nomTerrain ? reservationTerrainEntity.nomTerrain.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reservation-terrain" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reservation-terrain/${reservationTerrainEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReservationTerrainDetail;
