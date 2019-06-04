package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.ZosSystemService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.ZosSystemDTO;

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
 * REST controller for managing {@link com.zos.usercatalog.domain.ZosSystem}.
 */
@RestController
@RequestMapping("/api")
public class ZosSystemResource {

    private final Logger log = LoggerFactory.getLogger(ZosSystemResource.class);

    private static final String ENTITY_NAME = "zosSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZosSystemService zosSystemService;

    public ZosSystemResource(ZosSystemService zosSystemService) {
        this.zosSystemService = zosSystemService;
    }

    /**
     * {@code POST  /zos-systems} : Create a new zosSystem.
     *
     * @param zosSystemDTO the zosSystemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zosSystemDTO, or with status {@code 400 (Bad Request)} if the zosSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zos-systems")
    public ResponseEntity<ZosSystemDTO> createZosSystem(@Valid @RequestBody ZosSystemDTO zosSystemDTO) throws URISyntaxException {
        log.debug("REST request to save ZosSystem : {}", zosSystemDTO);
        if (zosSystemDTO.getId() != null) {
            throw new BadRequestAlertException("A new zosSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZosSystemDTO result = zosSystemService.save(zosSystemDTO);
        return ResponseEntity.created(new URI("/api/zos-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zos-systems} : Updates an existing zosSystem.
     *
     * @param zosSystemDTO the zosSystemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zosSystemDTO,
     * or with status {@code 400 (Bad Request)} if the zosSystemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zosSystemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zos-systems")
    public ResponseEntity<ZosSystemDTO> updateZosSystem(@Valid @RequestBody ZosSystemDTO zosSystemDTO) throws URISyntaxException {
        log.debug("REST request to update ZosSystem : {}", zosSystemDTO);
        if (zosSystemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZosSystemDTO result = zosSystemService.save(zosSystemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zosSystemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zos-systems} : get all the zosSystems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zosSystems in body.
     */
    @GetMapping("/zos-systems")
    public ResponseEntity<List<ZosSystemDTO>> getAllZosSystems(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of ZosSystems");
        Page<ZosSystemDTO> page = zosSystemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zos-systems/:id} : get the "id" zosSystem.
     *
     * @param id the id of the zosSystemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zosSystemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zos-systems/{id}")
    public ResponseEntity<ZosSystemDTO> getZosSystem(@PathVariable Long id) {
        log.debug("REST request to get ZosSystem : {}", id);
        Optional<ZosSystemDTO> zosSystemDTO = zosSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zosSystemDTO);
    }

    /**
     * {@code DELETE  /zos-systems/:id} : delete the "id" zosSystem.
     *
     * @param id the id of the zosSystemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zos-systems/{id}")
    public ResponseEntity<Void> deleteZosSystem(@PathVariable Long id) {
        log.debug("REST request to delete ZosSystem : {}", id);
        zosSystemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/zos-systems?query=:query} : search for the zosSystem corresponding
     * to the query.
     *
     * @param query the query of the zosSystem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/zos-systems")
    public ResponseEntity<List<ZosSystemDTO>> searchZosSystems(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of ZosSystems for query {}", query);
        Page<ZosSystemDTO> page = zosSystemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
