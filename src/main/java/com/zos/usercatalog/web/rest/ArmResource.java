package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.ArmService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.ArmDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.zos.usercatalog.domain.Arm}.
 */
@RestController
@RequestMapping("/api")
public class ArmResource {

    private final Logger log = LoggerFactory.getLogger(ArmResource.class);

    private static final String ENTITY_NAME = "arm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmService armService;

    public ArmResource(ArmService armService) {
        this.armService = armService;
    }

    /**
     * {@code POST  /arms} : Create a new arm.
     *
     * @param armDTO the armDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armDTO, or with status {@code 400 (Bad Request)} if the arm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arms")
    public ResponseEntity<ArmDTO> createArm(@Valid @RequestBody ArmDTO armDTO) throws URISyntaxException {
        log.debug("REST request to save Arm : {}", armDTO);
        if (armDTO.getId() != null) {
            throw new BadRequestAlertException("A new arm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmDTO result = armService.save(armDTO);
        return ResponseEntity.created(new URI("/api/arms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arms} : Updates an existing arm.
     *
     * @param armDTO the armDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armDTO,
     * or with status {@code 400 (Bad Request)} if the armDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arms")
    public ResponseEntity<ArmDTO> updateArm(@Valid @RequestBody ArmDTO armDTO) throws URISyntaxException {
        log.debug("REST request to update Arm : {}", armDTO);
        if (armDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmDTO result = armService.save(armDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arms} : get all the arms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arms in body.
     */
    @GetMapping("/arms")
    public ResponseEntity<List<ArmDTO>> getAllArms(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Arms");
        Page<ArmDTO> page;
        if (eagerload) {
            page = armService.findAllWithEagerRelationships(pageable);
        } else {
            page = armService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arms/:id} : get the "id" arm.
     *
     * @param id the id of the armDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arms/{id}")
    public ResponseEntity<ArmDTO> getArm(@PathVariable Long id) {
        log.debug("REST request to get Arm : {}", id);
        Optional<ArmDTO> armDTO = armService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armDTO);
    }

    /**
     * {@code DELETE  /arms/:id} : delete the "id" arm.
     *
     * @param id the id of the armDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arms/{id}")
    public ResponseEntity<Void> deleteArm(@PathVariable Long id) {
        log.debug("REST request to delete Arm : {}", id);
        armService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/arms?query=:query} : search for the arm corresponding
     * to the query.
     *
     * @param query the query of the arm search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/arms")
    public ResponseEntity<List<ArmDTO>> searchArms(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Arms for query {}", query);
        Page<ArmDTO> page = armService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
