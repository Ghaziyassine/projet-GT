import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPack } from 'app/shared/model/pack.model';
import { getEntity, updateEntity, createEntity, reset } from './pack.reducer';

export const PackUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const packEntity = useAppSelector(state => state.pack.entity);
  const loading = useAppSelector(state => state.pack.loading);
  const updating = useAppSelector(state => state.pack.updating);
  const updateSuccess = useAppSelector(state => state.pack.updateSuccess);

  const handleClose = () => {
    navigate('/pack');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.nbrDeMatches !== undefined && typeof values.nbrDeMatches !== 'number') {
      values.nbrDeMatches = Number(values.nbrDeMatches);
    }

    const entity = {
      ...packEntity,
      ...values,
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
          ...packEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="appApp.pack.home.createOrEditLabel" data-cy="PackCreateUpdateHeading">
            <Translate contentKey="appApp.pack.home.createOrEditLabel">Create or edit a Pack</Translate>
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
                  id="pack-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('appApp.pack.nomPack')} id="pack-nomPack" name="nomPack" data-cy="nomPack" type="text" />
              <ValidatedField label={translate('appApp.pack.tarif')} id="pack-tarif" name="tarif" data-cy="tarif" type="text" />
              <ValidatedField
                label={translate('appApp.pack.nbrDeMatches')}
                id="pack-nbrDeMatches"
                name="nbrDeMatches"
                data-cy="nbrDeMatches"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pack" replace color="info">
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

export default PackUpdate;
