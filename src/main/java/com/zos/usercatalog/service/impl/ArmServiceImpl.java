package com.zos.usercatalog.service.impl;

import com.zos.usercatalog.service.ArmService;
import com.zos.usercatalog.domain.Arm;
import com.zos.usercatalog.repository.ArmRepository;
import com.zos.usercatalog.repository.search.ArmSearchRepository;
import com.zos.usercatalog.service.dto.ArmDTO;
import com.zos.usercatalog.service.mapper.ArmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Arm}.
 */
@Service
@Transactional
public class ArmServiceImpl implements ArmService {

    private final Logger log = LoggerFactory.getLogger(ArmServiceImpl.class);

    private final ArmRepository armRepository;

    private final ArmMapper armMapper;

    private final ArmSearchRepository armSearchRepository;

    public ArmServiceImpl(ArmRepository armRepository, ArmMapper armMapper, ArmSearchRepository armSearchRepository) {
        this.armRepository = armRepository;
        this.armMapper = armMapper;
        this.armSearchRepository = armSearchRepository;
    }

    /**
     * Save a arm.
     *
     * @param armDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArmDTO save(ArmDTO armDTO) {
        log.debug("Request to save Arm : {}", armDTO);
        Arm arm = armMapper.toEntity(armDTO);
        arm = armRepository.save(arm);
        ArmDTO result = armMapper.toDto(arm);
        armSearchRepository.save(arm);
        return result;
    }

    /**
     * Get all the arms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Arms");
        return armRepository.findAll(pageable)
            .map(armMapper::toDto);
    }


    /**
     * Get one arm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ArmDTO> findOne(Long id) {
        log.debug("Request to get Arm : {}", id);
        return armRepository.findById(id)
            .map(armMapper::toDto);
    }

    /**
     * Delete the arm by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arm : {}", id);
        armRepository.deleteById(id);
        armSearchRepository.deleteById(id);
    }

    /**
     * Search for the arm corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArmDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Arms for query {}", query);
        return armSearchRepository.search(queryStringQuery(query), pageable)
            .map(armMapper::toDto);
    }
}
