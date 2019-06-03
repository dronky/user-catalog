package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.ZosSystem;
import com.zos.usercatalog.repository.ZosSystemRepository;
import com.zos.usercatalog.repository.search.ZosSystemSearchRepository;
import com.zos.usercatalog.service.ZosSystemService;
import com.zos.usercatalog.service.dto.ZosSystemDTO;
import com.zos.usercatalog.service.mapper.ZosSystemMapper;
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
 * Integration tests for the {@Link ZosSystemResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class ZosSystemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    @Autowired
    private ZosSystemRepository zosSystemRepository;

    @Autowired
    private ZosSystemMapper zosSystemMapper;

    @Autowired
    private ZosSystemService zosSystemService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.ZosSystemSearchRepositoryMockConfiguration
     */
    @Autowired
    private ZosSystemSearchRepository mockZosSystemSearchRepository;

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

    private MockMvc restZosSystemMockMvc;

    private ZosSystem zosSystem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZosSystemResource zosSystemResource = new ZosSystemResource(zosSystemService);
        this.restZosSystemMockMvc = MockMvcBuilders.standaloneSetup(zosSystemResource)
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
    public static ZosSystem createEntity(EntityManager em) {
        ZosSystem zosSystem = new ZosSystem()
            .name(DEFAULT_NAME)
            .ip(DEFAULT_IP);
        return zosSystem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZosSystem createUpdatedEntity(EntityManager em) {
        ZosSystem zosSystem = new ZosSystem()
            .name(UPDATED_NAME)
            .ip(UPDATED_IP);
        return zosSystem;
    }

    @BeforeEach
    public void initTest() {
        zosSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createZosSystem() throws Exception {
        int databaseSizeBeforeCreate = zosSystemRepository.findAll().size();

        // Create the ZosSystem
        ZosSystemDTO zosSystemDTO = zosSystemMapper.toDto(zosSystem);
        restZosSystemMockMvc.perform(post("/api/zos-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zosSystemDTO)))
            .andExpect(status().isCreated());

        // Validate the ZosSystem in the database
        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeCreate + 1);
        ZosSystem testZosSystem = zosSystemList.get(zosSystemList.size() - 1);
        assertThat(testZosSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testZosSystem.getIp()).isEqualTo(DEFAULT_IP);

        // Validate the ZosSystem in Elasticsearch
        verify(mockZosSystemSearchRepository, times(1)).save(testZosSystem);
    }

    @Test
    @Transactional
    public void createZosSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zosSystemRepository.findAll().size();

        // Create the ZosSystem with an existing ID
        zosSystem.setId(1L);
        ZosSystemDTO zosSystemDTO = zosSystemMapper.toDto(zosSystem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZosSystemMockMvc.perform(post("/api/zos-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zosSystemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZosSystem in the database
        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeCreate);

        // Validate the ZosSystem in Elasticsearch
        verify(mockZosSystemSearchRepository, times(0)).save(zosSystem);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = zosSystemRepository.findAll().size();
        // set the field null
        zosSystem.setName(null);

        // Create the ZosSystem, which fails.
        ZosSystemDTO zosSystemDTO = zosSystemMapper.toDto(zosSystem);

        restZosSystemMockMvc.perform(post("/api/zos-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zosSystemDTO)))
            .andExpect(status().isBadRequest());

        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZosSystems() throws Exception {
        // Initialize the database
        zosSystemRepository.saveAndFlush(zosSystem);

        // Get all the zosSystemList
        restZosSystemMockMvc.perform(get("/api/zos-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zosSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())));
    }
    
    @Test
    @Transactional
    public void getZosSystem() throws Exception {
        // Initialize the database
        zosSystemRepository.saveAndFlush(zosSystem);

        // Get the zosSystem
        restZosSystemMockMvc.perform(get("/api/zos-systems/{id}", zosSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zosSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZosSystem() throws Exception {
        // Get the zosSystem
        restZosSystemMockMvc.perform(get("/api/zos-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZosSystem() throws Exception {
        // Initialize the database
        zosSystemRepository.saveAndFlush(zosSystem);

        int databaseSizeBeforeUpdate = zosSystemRepository.findAll().size();

        // Update the zosSystem
        ZosSystem updatedZosSystem = zosSystemRepository.findById(zosSystem.getId()).get();
        // Disconnect from session so that the updates on updatedZosSystem are not directly saved in db
        em.detach(updatedZosSystem);
        updatedZosSystem
            .name(UPDATED_NAME)
            .ip(UPDATED_IP);
        ZosSystemDTO zosSystemDTO = zosSystemMapper.toDto(updatedZosSystem);

        restZosSystemMockMvc.perform(put("/api/zos-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zosSystemDTO)))
            .andExpect(status().isOk());

        // Validate the ZosSystem in the database
        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeUpdate);
        ZosSystem testZosSystem = zosSystemList.get(zosSystemList.size() - 1);
        assertThat(testZosSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZosSystem.getIp()).isEqualTo(UPDATED_IP);

        // Validate the ZosSystem in Elasticsearch
        verify(mockZosSystemSearchRepository, times(1)).save(testZosSystem);
    }

    @Test
    @Transactional
    public void updateNonExistingZosSystem() throws Exception {
        int databaseSizeBeforeUpdate = zosSystemRepository.findAll().size();

        // Create the ZosSystem
        ZosSystemDTO zosSystemDTO = zosSystemMapper.toDto(zosSystem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZosSystemMockMvc.perform(put("/api/zos-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zosSystemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZosSystem in the database
        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ZosSystem in Elasticsearch
        verify(mockZosSystemSearchRepository, times(0)).save(zosSystem);
    }

    @Test
    @Transactional
    public void deleteZosSystem() throws Exception {
        // Initialize the database
        zosSystemRepository.saveAndFlush(zosSystem);

        int databaseSizeBeforeDelete = zosSystemRepository.findAll().size();

        // Delete the zosSystem
        restZosSystemMockMvc.perform(delete("/api/zos-systems/{id}", zosSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ZosSystem> zosSystemList = zosSystemRepository.findAll();
        assertThat(zosSystemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ZosSystem in Elasticsearch
        verify(mockZosSystemSearchRepository, times(1)).deleteById(zosSystem.getId());
    }

    @Test
    @Transactional
    public void searchZosSystem() throws Exception {
        // Initialize the database
        zosSystemRepository.saveAndFlush(zosSystem);
        when(mockZosSystemSearchRepository.search(queryStringQuery("id:" + zosSystem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(zosSystem), PageRequest.of(0, 1), 1));
        // Search the zosSystem
        restZosSystemMockMvc.perform(get("/api/_search/zos-systems?query=id:" + zosSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zosSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZosSystem.class);
        ZosSystem zosSystem1 = new ZosSystem();
        zosSystem1.setId(1L);
        ZosSystem zosSystem2 = new ZosSystem();
        zosSystem2.setId(zosSystem1.getId());
        assertThat(zosSystem1).isEqualTo(zosSystem2);
        zosSystem2.setId(2L);
        assertThat(zosSystem1).isNotEqualTo(zosSystem2);
        zosSystem1.setId(null);
        assertThat(zosSystem1).isNotEqualTo(zosSystem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZosSystemDTO.class);
        ZosSystemDTO zosSystemDTO1 = new ZosSystemDTO();
        zosSystemDTO1.setId(1L);
        ZosSystemDTO zosSystemDTO2 = new ZosSystemDTO();
        assertThat(zosSystemDTO1).isNotEqualTo(zosSystemDTO2);
        zosSystemDTO2.setId(zosSystemDTO1.getId());
        assertThat(zosSystemDTO1).isEqualTo(zosSystemDTO2);
        zosSystemDTO2.setId(2L);
        assertThat(zosSystemDTO1).isNotEqualTo(zosSystemDTO2);
        zosSystemDTO1.setId(null);
        assertThat(zosSystemDTO1).isNotEqualTo(zosSystemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zosSystemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zosSystemMapper.fromId(null)).isNull();
    }
}
