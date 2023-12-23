import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pack.reducer';

export const PackDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const packEntity = useAppSelector(state => state.pack.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="packDetailsHeading">
          <Translate contentKey="appApp.pack.detail.title">Pack</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{packEntity.id}</dd>
          <dt>
            <span id="nomPack">
              <Translate contentKey="appApp.pack.nomPack">Nom Pack</Translate>
            </span>
          </dt>
          <dd>{packEntity.nomPack}</dd>
          <dt>
            <span id="tarif">
              <Translate contentKey="appApp.pack.tarif">Tarif</Translate>
            </span>
          </dt>
          <dd>{packEntity.tarif}</dd>
          <dt>
            <span id="nbrDeMatches">
              <Translate contentKey="appApp.pack.nbrDeMatches">Nbr De Matches</Translate>
            </span>
          </dt>
          <dd>{packEntity.nbrDeMatches}</dd>
        </dl>
        <Button tag={Link} to="/pack" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pack/${packEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PackDetail;
