import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReservation } from 'app/shared/model/reservation.model';
import { getEntities as getReservations } from 'app/entities/reservation/reservation.reducer';
import { ITerrain } from 'app/shared/model/terrain.model';
import { getEntities as getTerrains } from 'app/entities/terrain/terrain.reducer';
import { IReservationTerrain } from 'app/shared/model/reservation-terrain.model';
import { getEntity, updateEntity, createEntity, reset } from './reservation-terrain.reducer';

export const ReservationTerrainUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reservations = useAppSelector(state => state.reservation.entities);
  const terrains = useAppSelector(state => state.terrain.entities);
  const reservationTerrainEntity = useAppSelector(state => state.reservationTerrain.entity);
  const loading = useAppSelector(state => state.reservationTerrain.loading);
  const updating = useAppSelector(state => state.reservationTerrain.updating);
  const updateSuccess = useAppSelector(state => state.reservationTerrain.updateSuccess);

  const handleClose = () => {
    navigate('/reservation-terrain');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getReservations({}));
    dispatch(getTerrains({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.heure !== undefined && typeof values.heure !== 'number') {
      values.heure = Number(values.heure);
    }
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...reservationTerrainEntity,
      ...values,
      reservation: reservations.find(it => it.id.toString() === values.reservation.toString()),
      nomTerrain: terrains.find(it => it.id.toString() === values.nomTerrain.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...reservationTerrainEntity,
          date: convertDateTimeFromServer(reservationTerrainEntity.date),
          reservation: reservationTerrainEntity?.reservation?.id,
          nomTerrain: reservationTerrainEntity?.nomTerrain?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.reservationTerrain.home.createOrEditLabel" data-cy="ReservationTerrainCreateUpdateHeading">
            <Translate contentKey="appApp.reservationTerrain.home.createOrEditLabel">Create or edit a ReservationTerrain</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="reservation-terrain-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appApp.reservationTerrain.heure')}
                id="reservation-terrain-heure"
                name="heure"
                data-cy="heure"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.reservationTerrain.date')}
                id="reservation-terrain-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="reservation-terrain-reservation"
                name="reservation"
                data-cy="reservation"
                label={translate('appApp.reservationTerrain.reservation')}
                type="select"
              >
                <option value="" key="0" />
                {reservations
                  ? reservations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reservation-terrain-nomTerrain"
                name="nomTerrain"
                data-cy="nomTerrain"
                label={translate('appApp.reservationTerrain.nomTerrain')}
                type="select"
              >
                <option value="" key="0" />
                {terrains
                  ? terrains.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reservation-terrain" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReservationTerrainUpdate;
