import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClub } from 'app/shared/model/club.model';
import { getEntities as getClubs } from 'app/entities/club/club.reducer';
import { IZone } from 'app/shared/model/zone.model';
import { getEntities as getZones } from 'app/entities/zone/zone.reducer';
import { ITerrain } from 'app/shared/model/terrain.model';
import { getEntity, updateEntity, createEntity, reset } from './terrain.reducer';

export const TerrainUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clubs = useAppSelector(state => state.club.entities);
  const zones = useAppSelector(state => state.zone.entities);
  const terrainEntity = useAppSelector(state => state.terrain.entity);
  const loading = useAppSelector(state => state.terrain.loading);
  const updating = useAppSelector(state => state.terrain.updating);
  const updateSuccess = useAppSelector(state => state.terrain.updateSuccess);

  const handleClose = () => {
    navigate('/terrain');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClubs({}));
    dispatch(getZones({}));
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
    if (values.latitude !== undefined && typeof values.latitude !== 'number') {
      values.latitude = Number(values.latitude);
    }
    if (values.longitude !== undefined && typeof values.longitude !== 'number') {
      values.longitude = Number(values.longitude);
    }
    if (values.rank !== undefined && typeof values.rank !== 'number') {
      values.rank = Number(values.rank);
    }

    const entity = {
      ...terrainEntity,
      ...values,
      nomClub: clubs.find(it => it.id.toString() === values.nomClub.toString()),
      nomZone: zones.find(it => it.id.toString() === values.nomZone.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...terrainEntity,
          nomClub: terrainEntity?.nomClub?.id,
          nomZone: terrainEntity?.nomZone?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.terrain.home.createOrEditLabel" data-cy="TerrainCreateUpdateHeading">
            <Translate contentKey="appApp.terrain.home.createOrEditLabel">Create or edit a Terrain</Translate>
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
                  id="terrain-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('appApp.terrain.nomTerrain')}
                id="terrain-nomTerrain"
                name="nomTerrain"
                data-cy="nomTerrain"
                type="text"
              />
              <ValidatedBlobField
                label={translate('appApp.terrain.photo')}
                id="terrain-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('appApp.terrain.adresse')}
                id="terrain-adresse"
                name="adresse"
                data-cy="adresse"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.terrain.latitude')}
                id="terrain-latitude"
                name="latitude"
                data-cy="latitude"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.terrain.longitude')}
                id="terrain-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField label={translate('appApp.terrain.rank')} id="terrain-rank" name="rank" data-cy="rank" type="text" />
              <ValidatedField label={translate('appApp.terrain.type')} id="terrain-type" name="type" data-cy="type" type="text" />
              <ValidatedField label={translate('appApp.terrain.etat')} id="terrain-etat" name="etat" data-cy="etat" type="text" />
              <ValidatedField
                label={translate('appApp.terrain.description')}
                id="terrain-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('appApp.terrain.typeSal')}
                id="terrain-typeSal"
                name="typeSal"
                data-cy="typeSal"
                type="text"
              />
              <ValidatedField label={translate('appApp.terrain.tarif')} id="terrain-tarif" name="tarif" data-cy="tarif" type="text" />
              <ValidatedField
                id="terrain-nomClub"
                name="nomClub"
                data-cy="nomClub"
                label={translate('appApp.terrain.nomClub')}
                type="select"
              >
                <option value="" key="0" />
                {clubs
                  ? clubs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="terrain-nomZone"
                name="nomZone"
                data-cy="nomZone"
                label={translate('appApp.terrain.nomZone')}
                type="select"
              >
                <option value="" key="0" />
                {zones
                  ? zones.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/terrain" replace color="info">
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

export default TerrainUpdate;
