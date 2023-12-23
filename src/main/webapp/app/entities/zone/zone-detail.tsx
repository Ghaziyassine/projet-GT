import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './zone.reducer';

export const ZoneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const zoneEntity = useAppSelector(state => state.zone.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="zoneDetailsHeading">
          <Translate contentKey="appApp.zone.detail.title">Zone</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.id}</dd>
          <dt>
            <span id="nomZone">
              <Translate contentKey="appApp.zone.nomZone">Nom Zone</Translate>
            </span>
          </dt>
          <dd>{zoneEntity.nomZone}</dd>
          <dt>
            <Translate contentKey="appApp.zone.nomVille">Nom Ville</Translate>
          </dt>
          <dd>{zoneEntity.nomVille ? zoneEntity.nomVille.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/zone" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/zone/${zoneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ZoneDetail;
