import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/club">
        <Translate contentKey="global.menu.entities.club" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pack">
        <Translate contentKey="global.menu.entities.pack" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/pack-client">
        <Translate contentKey="global.menu.entities.packClient" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/photo">
        <Translate contentKey="global.menu.entities.photo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reservation">
        <Translate contentKey="global.menu.entities.reservation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reservation-terrain">
        <Translate contentKey="global.menu.entities.reservationTerrain" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/terrain">
        <Translate contentKey="global.menu.entities.terrain" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/ville">
        <Translate contentKey="global.menu.entities.ville" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/zone">
        <Translate contentKey="global.menu.entities.zone" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
