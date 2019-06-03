package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.service.ArmIpService;
import com.zos.usercatalog.web.rest.errors.BadRequestAlertException;
import com.zos.usercatalog.service.dto.ArmIpDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link com.zos.usercatalog.domain.ArmIp}.
 */
@RestController
@RequestMapping("/api")
public class ArmIpResource {

    private final Logger log = LoggerFactory.getLogger(ArmIpResource.class);

    private static final String ENTITY_NAME = "armIp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmIpService armIpService;

    public ArmIpResource(ArmIpService armIpService) {
        this.armIpService = armIpService;
    }

    /**
     * {@code POST  /arm-ips} : Create a new armIp.
     *
     * @param armIpDTO the armIpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armIpDTO, or with status {@code 400 (Bad Request)} if the armIp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arm-ips")
    public ResponseEntity<ArmIpDTO> createArmIp(@Valid @RequestBody ArmIpDTO armIpDTO) throws URISyntaxException {
        log.debug("REST request to save ArmIp : {}", armIpDTO);
        if (armIpDTO.getId() != null) {
            throw new BadRequestAlertException("A new armIp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmIpDTO result = armIpService.save(armIpDTO);
        return ResponseEntity.created(new URI("/api/arm-ips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arm-ips} : Updates an existing armIp.
     *
     * @param armIpDTO the armIpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armIpDTO,
     * or with status {@code 400 (Bad Request)} if the armIpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armIpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arm-ips")
    public ResponseEntity<ArmIpDTO> updateArmIp(@Valid @RequestBody ArmIpDTO armIpDTO) throws URISyntaxException {
        log.debug("REST request to update ArmIp : {}", armIpDTO);
        if (armIpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmIpDTO result = armIpService.save(armIpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armIpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arm-ips} : get all the armIps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armIps in body.
     */
    @GetMapping("/arm-ips")
    public List<ArmIpDTO> getAllArmIps() {
        log.debug("REST request to get all ArmIps");
        return armIpService.findAll();
    }

    /**
     * {@code GET  /arm-ips/:id} : get the "id" armIp.
     *
     * @param id the id of the armIpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armIpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arm-ips/{id}")
    public ResponseEntity<ArmIpDTO> getArmIp(@PathVariable Long id) {
        log.debug("REST request to get ArmIp : {}", id);
        Optional<ArmIpDTO> armIpDTO = armIpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armIpDTO);
    }

    /**
     * {@code DELETE  /arm-ips/:id} : delete the "id" armIp.
     *
     * @param id the id of the armIpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arm-ips/{id}")
    public ResponseEntity<Void> deleteArmIp(@PathVariable Long id) {
        log.debug("REST request to delete ArmIp : {}", id);
        armIpService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/arm-ips?query=:query} : search for the armIp corresponding
     * to the query.
     *
     * @param query the query of the armIp search.
     * @return the result of the search.
     */
    @GetMapping("/_search/arm-ips")
    public List<ArmIpDTO> searchArmIps(@RequestParam String query) {
        log.debug("REST request to search ArmIps for query {}", query);
        return armIpService.search(query);
    }

}
