package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.PackClient;
import com.mycompany.myapp.repository.PackClientRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.PackClient}.
 */
@RestController
@RequestMapping("/api/pack-clients")
@Transactional
public class PackClientResource {

    private final Logger log = LoggerFactory.getLogger(PackClientResource.class);

    private static final String ENTITY_NAME = "packClient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackClientRepository packClientRepository;

    public PackClientResource(PackClientRepository packClientRepository) {
        this.packClientRepository = packClientRepository;
    }

    /**
     * {@code POST  /pack-clients} : Create a new packClient.
     *
     * @param packClient the packClient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packClient, or with status {@code 400 (Bad Request)} if the packClient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PackClient> createPackClient(@RequestBody PackClient packClient) throws URISyntaxException {
        log.debug("REST request to save PackClient : {}", packClient);
        if (packClient.getId() != null) {
            throw new BadRequestAlertException("A new packClient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PackClient result = packClientRepository.save(packClient);
        return ResponseEntity
            .created(new URI("/api/pack-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pack-clients/:id} : Updates an existing packClient.
     *
     * @param id the id of the packClient to save.
     * @param packClient the packClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packClient,
     * or with status {@code 400 (Bad Request)} if the packClient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PackClient> updatePackClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PackClient packClient
    ) throws URISyntaxException {
        log.debug("REST request to update PackClient : {}, {}", id, packClient);
        if (packClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PackClient result = packClientRepository.save(packClient);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packClient.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pack-clients/:id} : Partial updates given fields of an existing packClient, field will ignore if it is null
     *
     * @param id the id of the packClient to save.
     * @param packClient the packClient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packClient,
     * or with status {@code 400 (Bad Request)} if the packClient is not valid,
     * or with status {@code 404 (Not Found)} if the packClient is not found,
     * or with status {@code 500 (Internal Server Error)} if the packClient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PackClient> partialUpdatePackClient(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PackClient packClient
    ) throws URISyntaxException {
        log.debug("REST request to partial update PackClient partially : {}, {}", id, packClient);
        if (packClient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, packClient.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!packClientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PackClient> result = packClientRepository
            .findById(packClient.getId())
            .map(existingPackClient -> {
                if (packClient.getDate() != null) {
                    existingPackClient.setDate(packClient.getDate());
                }

                return existingPackClient;
            })
            .map(packClientRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packClient.getId().toString())
        );
    }

    /**
     * {@code GET  /pack-clients} : get all the packClients.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packClients in body.
     */
    @GetMapping("")
    public List<PackClient> getAllPackClients() {
        log.debug("REST request to get all PackClients");
        return packClientRepository.findAll();
    }

    /**
     * {@code GET  /pack-clients/:id} : get the "id" packClient.
     *
     * @param id the id of the packClient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packClient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PackClient> getPackClient(@PathVariable("id") Long id) {
        log.debug("REST request to get PackClient : {}", id);
        Optional<PackClient> packClient = packClientRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(packClient);
    }

    /**
     * {@code DELETE  /pack-clients/:id} : delete the "id" packClient.
     *
     * @param id the id of the packClient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackClient(@PathVariable("id") Long id) {
        log.debug("REST request to delete PackClient : {}", id);
        packClientRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
