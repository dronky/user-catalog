package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.System;
import com.zos.usercatalog.repository.SystemRepository;
import com.zos.usercatalog.repository.search.SystemSearchRepository;
import com.zos.usercatalog.service.SystemService;
import com.zos.usercatalog.service.dto.SystemDTO;
import com.zos.usercatalog.service.mapper.SystemMapper;
import com.zos.usercatalog.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.zos.usercatalog.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SystemResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class SystemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    @Autowired
    private SystemRepository systemRepository;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SystemService systemService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.SystemSearchRepositoryMockConfiguration
     */
    @Autowired
    private SystemSearchRepository mockSystemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSystemMockMvc;

    private System system;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SystemResource systemResource = new SystemResource(systemService);
        this.restSystemMockMvc = MockMvcBuilders.standaloneSetup(systemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createEntity(EntityManager em) {
        System system = new System()
            .name(DEFAULT_NAME)
            .ip(DEFAULT_IP);
        return system;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static System createUpdatedEntity(EntityManager em) {
        System system = new System()
            .name(UPDATED_NAME)
            .ip(UPDATED_IP);
        return system;
    }

    @BeforeEach
    public void initTest() {
        system = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystem() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isCreated());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate + 1);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSystem.getIp()).isEqualTo(DEFAULT_IP);

        // Validate the System in Elasticsearch
        verify(mockSystemSearchRepository, times(1)).save(testSystem);
    }

    @Test
    @Transactional
    public void createSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemRepository.findAll().size();

        // Create the System with an existing ID
        system.setId(1L);
        SystemDTO systemDTO = systemMapper.toDto(system);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeCreate);

        // Validate the System in Elasticsearch
        verify(mockSystemSearchRepository, times(0)).save(system);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemRepository.findAll().size();
        // set the field null
        system.setName(null);

        // Create the System, which fails.
        SystemDTO systemDTO = systemMapper.toDto(system);

        restSystemMockMvc.perform(post("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystems() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get all the systemList
        restSystemMockMvc.perform(get("/api/systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())));
    }
    
    @Test
    @Transactional
    public void getSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(system.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystem() throws Exception {
        // Get the system
        restSystemMockMvc.perform(get("/api/systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Update the system
        System updatedSystem = systemRepository.findById(system.getId()).get();
        // Disconnect from session so that the updates on updatedSystem are not directly saved in db
        em.detach(updatedSystem);
        updatedSystem
            .name(UPDATED_NAME)
            .ip(UPDATED_IP);
        SystemDTO systemDTO = systemMapper.toDto(updatedSystem);

        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isOk());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);
        System testSystem = systemList.get(systemList.size() - 1);
        assertThat(testSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSystem.getIp()).isEqualTo(UPDATED_IP);

        // Validate the System in Elasticsearch
        verify(mockSystemSearchRepository, times(1)).save(testSystem);
    }

    @Test
    @Transactional
    public void updateNonExistingSystem() throws Exception {
        int databaseSizeBeforeUpdate = systemRepository.findAll().size();

        // Create the System
        SystemDTO systemDTO = systemMapper.toDto(system);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemMockMvc.perform(put("/api/systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the System in the database
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the System in Elasticsearch
        verify(mockSystemSearchRepository, times(0)).save(system);
    }

    @Test
    @Transactional
    public void deleteSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);

        int databaseSizeBeforeDelete = systemRepository.findAll().size();

        // Delete the system
        restSystemMockMvc.perform(delete("/api/systems/{id}", system.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<System> systemList = systemRepository.findAll();
        assertThat(systemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the System in Elasticsearch
        verify(mockSystemSearchRepository, times(1)).deleteById(system.getId());
    }

    @Test
    @Transactional
    public void searchSystem() throws Exception {
        // Initialize the database
        systemRepository.saveAndFlush(system);
        when(mockSystemSearchRepository.search(queryStringQuery("id:" + system.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(system), PageRequest.of(0, 1), 1));
        // Search the system
        restSystemMockMvc.perform(get("/api/_search/systems?query=id:" + system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(system.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(System.class);
        System system1 = new System();
        system1.setId(1L);
        System system2 = new System();
        system2.setId(system1.getId());
        assertThat(system1).isEqualTo(system2);
        system2.setId(2L);
        assertThat(system1).isNotEqualTo(system2);
        system1.setId(null);
        assertThat(system1).isNotEqualTo(system2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemDTO.class);
        SystemDTO systemDTO1 = new SystemDTO();
        systemDTO1.setId(1L);
        SystemDTO systemDTO2 = new SystemDTO();
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO2.setId(systemDTO1.getId());
        assertThat(systemDTO1).isEqualTo(systemDTO2);
        systemDTO2.setId(2L);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
        systemDTO1.setId(null);
        assertThat(systemDTO1).isNotEqualTo(systemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(systemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(systemMapper.fromId(null)).isNull();
    }
}
