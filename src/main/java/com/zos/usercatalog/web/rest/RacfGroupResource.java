package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.RacfGroupService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.RacfGroupDTO;

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
 * REST controller for managing {@link com.zos.usercatalog.domain.RacfGroup}.
 */
@RestController
@RequestMapping("/api")
public class RacfGroupResource {

    private final Logger log = LoggerFactory.getLogger(RacfGroupResource.class);

    private static final String ENTITY_NAME = "racfGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacfGroupService racfGroupService;

    public RacfGroupResource(RacfGroupService racfGroupService) {
        this.racfGroupService = racfGroupService;
    }

    /**
     * {@code POST  /racf-groups} : Create a new racfGroup.
     *
     * @param racfGroupDTO the racfGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racfGroupDTO, or with status {@code 400 (Bad Request)} if the racfGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racf-groups")
    public ResponseEntity<RacfGroupDTO> createRacfGroup(@Valid @RequestBody RacfGroupDTO racfGroupDTO) throws URISyntaxException {
        log.debug("REST request to save RacfGroup : {}", racfGroupDTO);
        if (racfGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new racfGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RacfGroupDTO result = racfGroupService.save(racfGroupDTO);
        return ResponseEntity.created(new URI("/api/racf-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racf-groups} : Updates an existing racfGroup.
     *
     * @param racfGroupDTO the racfGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racfGroupDTO,
     * or with status {@code 400 (Bad Request)} if the racfGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racfGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racf-groups")
    public ResponseEntity<RacfGroupDTO> updateRacfGroup(@Valid @RequestBody RacfGroupDTO racfGroupDTO) throws URISyntaxException {
        log.debug("REST request to update RacfGroup : {}", racfGroupDTO);
        if (racfGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RacfGroupDTO result = racfGroupService.save(racfGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racfGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racf-groups} : get all the racfGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racfGroups in body.
     */
    @GetMapping("/racf-groups")
    public ResponseEntity<List<RacfGroupDTO>> getAllRacfGroups(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of RacfGroups");
        Page<RacfGroupDTO> page = racfGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racf-groups/:id} : get the "id" racfGroup.
     *
     * @param id the id of the racfGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racfGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racf-groups/{id}")
    public ResponseEntity<RacfGroupDTO> getRacfGroup(@PathVariable Long id) {
        log.debug("REST request to get RacfGroup : {}", id);
        Optional<RacfGroupDTO> racfGroupDTO = racfGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(racfGroupDTO);
    }

    /**
     * {@code DELETE  /racf-groups/:id} : delete the "id" racfGroup.
     *
     * @param id the id of the racfGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racf-groups/{id}")
    public ResponseEntity<Void> deleteRacfGroup(@PathVariable Long id) {
        log.debug("REST request to delete RacfGroup : {}", id);
        racfGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/racf-groups?query=:query} : search for the racfGroup corresponding
     * to the query.
     *
     * @param query the query of the racfGroup search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/racf-groups")
    public ResponseEntity<List<RacfGroupDTO>> searchRacfGroups(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of RacfGroups for query {}", query);
        Page<RacfGroupDTO> page = racfGroupService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
