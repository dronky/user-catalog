package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.RacfUserService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.RacfUserDTO;

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
 * REST controller for managing {@link com.zos.usercatalog.domain.RacfUser}.
 */
@RestController
@RequestMapping("/api")
public class RacfUserResource {

    private final Logger log = LoggerFactory.getLogger(RacfUserResource.class);

    private static final String ENTITY_NAME = "racfUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacfUserService racfUserService;

    public RacfUserResource(RacfUserService racfUserService) {
        this.racfUserService = racfUserService;
    }

    /**
     * {@code POST  /racf-users} : Create a new racfUser.
     *
     * @param racfUserDTO the racfUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racfUserDTO, or with status {@code 400 (Bad Request)} if the racfUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racf-users")
    public ResponseEntity<RacfUserDTO> createRacfUser(@Valid @RequestBody RacfUserDTO racfUserDTO) throws URISyntaxException {
        log.debug("REST request to save RacfUser : {}", racfUserDTO);
        if (racfUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new racfUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacfUserDTO result = racfUserService.save(racfUserDTO);
        return ResponseEntity.created(new URI("/api/racf-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racf-users} : Updates an existing racfUser.
     *
     * @param racfUserDTO the racfUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racfUserDTO,
     * or with status {@code 400 (Bad Request)} if the racfUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racfUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racf-users")
    public ResponseEntity<RacfUserDTO> updateRacfUser(@Valid @RequestBody RacfUserDTO racfUserDTO) throws URISyntaxException {
        log.debug("REST request to update RacfUser : {}", racfUserDTO);
        if (racfUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacfUserDTO result = racfUserService.save(racfUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racfUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racf-users} : get all the racfUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racfUsers in body.
     */
    @GetMapping("/racf-users")
    public ResponseEntity<List<RacfUserDTO>> getAllRacfUsers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RacfUsers");
        Page<RacfUserDTO> page;
        if (eagerload) {
            page = racfUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = racfUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racf-users/:id} : get the "id" racfUser.
     *
     * @param id the id of the racfUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racfUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racf-users/{id}")
    public ResponseEntity<RacfUserDTO> getRacfUser(@PathVariable Long id) {
        log.debug("REST request to get RacfUser : {}", id);
        Optional<RacfUserDTO> racfUserDTO = racfUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racfUserDTO);
    }

    /**
     * {@code DELETE  /racf-users/:id} : delete the "id" racfUser.
     *
     * @param id the id of the racfUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racf-users/{id}")
    public ResponseEntity<Void> deleteRacfUser(@PathVariable Long id) {
        log.debug("REST request to delete RacfUser : {}", id);
        racfUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/racf-users?query=:query} : search for the racfUser corresponding
     * to the query.
     *
     * @param query the query of the racfUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/racf-users")
    public ResponseEntity<List<RacfUserDTO>> searchRacfUsers(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of RacfUsers for query {}", query);
        Page<RacfUserDTO> page = racfUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
