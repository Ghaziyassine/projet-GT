package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PackClient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PackClient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackClientRepository extends JpaRepository<PackClient, Long> {}
