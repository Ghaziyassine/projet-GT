import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pack-client.reducer';

export const PackClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const packClientEntity = useAppSelector(state => state.packClient.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="packClientDetailsHeading">
          <Translate contentKey="appApp.packClient.detail.title">PackClient</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{packClientEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="appApp.packClient.date">Date</Translate>
            </span>
          </dt>
          <dd>{packClientEntity.date ? <TextFormat value={packClientEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="appApp.packClient.nomClient">Nom Client</Translate>
          </dt>
          <dd>{packClientEntity.nomClient ? packClientEntity.nomClient.id : ''}</dd>
          <dt>
            <Translate contentKey="appApp.packClient.nomPack">Nom Pack</Translate>
          </dt>
          <dd>{packClientEntity.nomPack ? packClientEntity.nomPack.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pack-client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pack-client/${packClientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PackClientDetail;
