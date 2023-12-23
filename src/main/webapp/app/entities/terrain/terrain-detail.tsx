import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './terrain.reducer';

export const TerrainDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const terrainEntity = useAppSelector(state => state.terrain.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="terrainDetailsHeading">
          <Translate contentKey="appApp.terrain.detail.title">Terrain</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.id}</dd>
          <dt>
            <span id="nomTerrain">
              <Translate contentKey="appApp.terrain.nomTerrain">Nom Terrain</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.nomTerrain}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="appApp.terrain.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {terrainEntity.photo ? (
              <div>
                {terrainEntity.photoContentType ? (
                  <a onClick={openFile(terrainEntity.photoContentType, terrainEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {terrainEntity.photoContentType}, {byteSize(terrainEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="appApp.terrain.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.adresse}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="appApp.terrain.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="appApp.terrain.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.longitude}</dd>
          <dt>
            <span id="rank">
              <Translate contentKey="appApp.terrain.rank">Rank</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.rank}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="appApp.terrain.type">Type</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.type}</dd>
          <dt>
            <span id="etat">
              <Translate contentKey="appApp.terrain.etat">Etat</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.etat}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="appApp.terrain.description">Description</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.description}</dd>
          <dt>
            <span id="typeSal">
              <Translate contentKey="appApp.terrain.typeSal">Type Sal</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.typeSal}</dd>
          <dt>
            <span id="tarif">
              <Translate contentKey="appApp.terrain.tarif">Tarif</Translate>
            </span>
          </dt>
          <dd>{terrainEntity.tarif}</dd>
          <dt>
            <Translate contentKey="appApp.terrain.nomClub">Nom Club</Translate>
          </dt>
          <dd>{terrainEntity.nomClub ? terrainEntity.nomClub.id : ''}</dd>
          <dt>
            <Translate contentKey="appApp.terrain.nomZone">Nom Zone</Translate>
          </dt>
          <dd>{terrainEntity.nomZone ? terrainEntity.nomZone.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/terrain" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/terrain/${terrainEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TerrainDetail;
