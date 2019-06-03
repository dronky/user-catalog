package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.SystemService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.SystemDTO;

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
 * REST controller for managing {@link com.zos.usercatalog.domain.System}.
 */
@RestController
@RequestMapping("/api")
public class SystemResource {

    private final Logger log = LoggerFactory.getLogger(SystemResource.class);

    private static final String ENTITY_NAME = "system";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemService systemService;

    public SystemResource(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * {@code POST  /systems} : Create a new system.
     *
     * @param systemDTO the systemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemDTO, or with status {@code 400 (Bad Request)} if the system has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/systems")
    public ResponseEntity<SystemDTO> createSystem(@Valid @RequestBody SystemDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to save System : {}", systemDTO);
        if (systemDTO.getId() != null) {
            throw new BadRequestAlertException("A new system cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemDTO result = systemService.save(systemDTO);
        return ResponseEntity.created(new URI("/api/systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /systems} : Updates an existing system.
     *
     * @param systemDTO the systemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemDTO,
     * or with status {@code 400 (Bad Request)} if the systemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/systems")
    public ResponseEntity<SystemDTO> updateSystem(@Valid @RequestBody SystemDTO systemDTO) throws URISyntaxException {
        log.debug("REST request to update System : {}", systemDTO);
        if (systemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemDTO result = systemService.save(systemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /systems} : get all the systems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systems in body.
     */
    @GetMapping("/systems")
    public ResponseEntity<List<SystemDTO>> getAllSystems(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Systems");
        Page<SystemDTO> page = systemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /systems/:id} : get the "id" system.
     *
     * @param id the id of the systemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/systems/{id}")
    public ResponseEntity<SystemDTO> getSystem(@PathVariable Long id) {
        log.debug("REST request to get System : {}", id);
        Optional<SystemDTO> systemDTO = systemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemDTO);
    }

    /**
     * {@code DELETE  /systems/:id} : delete the "id" system.
     *
     * @param id the id of the systemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/systems/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable Long id) {
        log.debug("REST request to delete System : {}", id);
        systemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/systems?query=:query} : search for the system corresponding
     * to the query.
     *
     * @param query the query of the system search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/systems")
    public ResponseEntity<List<SystemDTO>> searchSystems(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of Systems for query {}", query);
        Page<SystemDTO> page = systemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
