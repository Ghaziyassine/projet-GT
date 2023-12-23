package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ReservationTerrain;
import com.mycompany.myapp.repository.ReservationTerrainRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ReservationTerrain}.
 */
@RestController
@RequestMapping("/api/reservation-terrains")
@Transactional
public class ReservationTerrainResource {

    private final Logger log = LoggerFactory.getLogger(ReservationTerrainResource.class);

    private static final String ENTITY_NAME = "reservationTerrain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReservationTerrainRepository reservationTerrainRepository;

    public ReservationTerrainResource(ReservationTerrainRepository reservationTerrainRepository) {
        this.reservationTerrainRepository = reservationTerrainRepository;
    }

    /**
     * {@code POST  /reservation-terrains} : Create a new reservationTerrain.
     *
     * @param reservationTerrain the reservationTerrain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reservationTerrain, or with status {@code 400 (Bad Request)} if the reservationTerrain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ReservationTerrain> createReservationTerrain(@RequestBody ReservationTerrain reservationTerrain)
        throws URISyntaxException {
        log.debug("REST request to save ReservationTerrain : {}", reservationTerrain);
        if (reservationTerrain.getId() != null) {
            throw new BadRequestAlertException("A new reservationTerrain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReservationTerrain result = reservationTerrainRepository.save(reservationTerrain);
        return ResponseEntity
            .created(new URI("/api/reservation-terrains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reservation-terrains/:id} : Updates an existing reservationTerrain.
     *
     * @param id the id of the reservationTerrain to save.
     * @param reservationTerrain the reservationTerrain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTerrain,
     * or with status {@code 400 (Bad Request)} if the reservationTerrain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reservationTerrain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReservationTerrain> updateReservationTerrain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationTerrain reservationTerrain
    ) throws URISyntaxException {
        log.debug("REST request to update ReservationTerrain : {}, {}", id, reservationTerrain);
        if (reservationTerrain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationTerrain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationTerrainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReservationTerrain result = reservationTerrainRepository.save(reservationTerrain);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTerrain.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reservation-terrains/:id} : Partial updates given fields of an existing reservationTerrain, field will ignore if it is null
     *
     * @param id the id of the reservationTerrain to save.
     * @param reservationTerrain the reservationTerrain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reservationTerrain,
     * or with status {@code 400 (Bad Request)} if the reservationTerrain is not valid,
     * or with status {@code 404 (Not Found)} if the reservationTerrain is not found,
     * or with status {@code 500 (Internal Server Error)} if the reservationTerrain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReservationTerrain> partialUpdateReservationTerrain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReservationTerrain reservationTerrain
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReservationTerrain partially : {}, {}", id, reservationTerrain);
        if (reservationTerrain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reservationTerrain.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reservationTerrainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReservationTerrain> result = reservationTerrainRepository
            .findById(reservationTerrain.getId())
            .map(existingReservationTerrain -> {
                if (reservationTerrain.getHeure() != null) {
                    existingReservationTerrain.setHeure(reservationTerrain.getHeure());
                }
                if (reservationTerrain.getDate() != null) {
                    existingReservationTerrain.setDate(reservationTerrain.getDate());
                }

                return existingReservationTerrain;
            })
            .map(reservationTerrainRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reservationTerrain.getId().toString())
        );
    }

    /**
     * {@code GET  /reservation-terrains} : get all the reservationTerrains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reservationTerrains in body.
     */
    @GetMapping("")
    public List<ReservationTerrain> getAllReservationTerrains() {
        log.debug("REST request to get all ReservationTerrains");
        return reservationTerrainRepository.findAll();
    }

    /**
     * {@code GET  /reservation-terrains/:id} : get the "id" reservationTerrain.
     *
     * @param id the id of the reservationTerrain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reservationTerrain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationTerrain> getReservationTerrain(@PathVariable("id") Long id) {
        log.debug("REST request to get ReservationTerrain : {}", id);
        Optional<ReservationTerrain> reservationTerrain = reservationTerrainRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reservationTerrain);
    }

    /**
     * {@code DELETE  /reservation-terrains/:id} : delete the "id" reservationTerrain.
     *
     * @param id the id of the reservationTerrain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTerrain(@PathVariable("id") Long id) {
        log.debug("REST request to delete ReservationTerrain : {}", id);
        reservationTerrainRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
