package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Terrain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Terrain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerrainRepository extends JpaRepository<Terrain, Long> {}
