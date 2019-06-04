package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.ArmIpService;
import com.zos.usercatalog.domain.ArmIp;
import com.zos.usercatalog.repository.ArmIpRepository;
import com.zos.usercatalog.repository.search.ArmIpSearchRepository;
import com.zos.usercatalog.service.dto.ArmIpDTO;
import com.zos.usercatalog.service.mapper.ArmIpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ArmIp}.
 */
@Service
@Transactional
public class ArmIpServiceImpl implements ArmIpService {

    private final Logger log = LoggerFactory.getLogger(ArmIpServiceImpl.class);

    private final ArmIpRepository armIpRepository;

    private final ArmIpMapper armIpMapper;

    private final ArmIpSearchRepository armIpSearchRepository;

    public ArmIpServiceImpl(ArmIpRepository armIpRepository, ArmIpMapper armIpMapper, ArmIpSearchRepository armIpSearchRepository) {
        this.armIpRepository = armIpRepository;
        this.armIpMapper = armIpMapper;
        this.armIpSearchRepository = armIpSearchRepository;
    }

    /**
     * Save a armIp.
     *
     * @param armIpDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArmIpDTO save(ArmIpDTO armIpDTO) {
        log.debug("Request to save ArmIp : {}", armIpDTO);
        ArmIp armIp = armIpMapper.toEntity(armIpDTO);
        armIp = armIpRepository.save(armIp);
        ArmIpDTO result = armIpMapper.toDto(armIp);
        armIpSearchRepository.save(armIp);
        return result;
    }

    /**
     * Get all the armIps.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArmIpDTO> findAll() {
        log.debug("Request to get all ArmIps");
        return armIpRepository.findAll().stream()
            .map(armIpMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one armIp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArmIpDTO> findOne(Long id) {
        log.debug("Request to get ArmIp : {}", id);
        return armIpRepository.findById(id)
            .map(armIpMapper::toDto);
    }

    /**
     * Delete the armIp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArmIp : {}", id);
        armIpRepository.deleteById(id);
        armIpSearchRepository.deleteById(id);
    }

    /**
     * Search for the armIp corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArmIpDTO> search(String query) {
        log.debug("Request to search ArmIps for query {}", query);
        return StreamSupport
            .stream(armIpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(armIpMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Search for the armIp corresponding to the query.
     *
     * @param armId the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<ArmIp> findByArmId(Long armId) {
        List<ArmIp> armIps = armIpRepository.findByArmId(armId);
        return armIps;
    }
}
