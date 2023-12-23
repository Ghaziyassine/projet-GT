package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ReservationTerrain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReservationTerrain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReservationTerrainRepository extends JpaRepository<ReservationTerrain, Long> {}
