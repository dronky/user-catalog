package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.RacfGroup;
import com.zos.usercatalog.repository.RacfGroupRepository;
import com.zos.usercatalog.repository.search.RacfGroupSearchRepository;
import com.zos.usercatalog.service.RacfGroupService;
import com.zos.usercatalog.service.dto.RacfGroupDTO;
import com.zos.usercatalog.service.mapper.RacfGroupMapper;
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
 * Integration tests for the {@Link RacfGroupResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class RacfGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBB";

    private static final Integer DEFAULT_GID = 1;
    private static final Integer UPDATED_GID = 2;

    @Autowired
    private RacfGroupRepository racfGroupRepository;

    @Autowired
    private RacfGroupMapper racfGroupMapper;

    @Autowired
    private RacfGroupService racfGroupService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.RacfGroupSearchRepositoryMockConfiguration
     */
    @Autowired
    private RacfGroupSearchRepository mockRacfGroupSearchRepository;

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

    private MockMvc restRacfGroupMockMvc;

    private RacfGroup racfGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacfGroupResource racfGroupResource = new RacfGroupResource(racfGroupService);
        this.restRacfGroupMockMvc = MockMvcBuilders.standaloneSetup(racfGroupResource)
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
    public static RacfGroup createEntity(EntityManager em) {
        RacfGroup racfGroup = new RacfGroup()
            .name(DEFAULT_NAME)
            .gid(DEFAULT_GID);
        return racfGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacfGroup createUpdatedEntity(EntityManager em) {
        RacfGroup racfGroup = new RacfGroup()
            .name(UPDATED_NAME)
            .gid(UPDATED_GID);
        return racfGroup;
    }

    @BeforeEach
    public void initTest() {
        racfGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacfGroup() throws Exception {
        int databaseSizeBeforeCreate = racfGroupRepository.findAll().size();

        // Create the RacfGroup
        RacfGroupDTO racfGroupDTO = racfGroupMapper.toDto(racfGroup);
        restRacfGroupMockMvc.perform(post("/api/racf-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the RacfGroup in the database
        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RacfGroup testRacfGroup = racfGroupList.get(racfGroupList.size() - 1);
        assertThat(testRacfGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRacfGroup.getGid()).isEqualTo(DEFAULT_GID);

        // Validate the RacfGroup in Elasticsearch
        verify(mockRacfGroupSearchRepository, times(1)).save(testRacfGroup);
    }

    @Test
    @Transactional
    public void createRacfGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racfGroupRepository.findAll().size();

        // Create the RacfGroup with an existing ID
        racfGroup.setId(1L);
        RacfGroupDTO racfGroupDTO = racfGroupMapper.toDto(racfGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacfGroupMockMvc.perform(post("/api/racf-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacfGroup in the database
        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeCreate);

        // Validate the RacfGroup in Elasticsearch
        verify(mockRacfGroupSearchRepository, times(0)).save(racfGroup);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = racfGroupRepository.findAll().size();
        // set the field null
        racfGroup.setName(null);

        // Create the RacfGroup, which fails.
        RacfGroupDTO racfGroupDTO = racfGroupMapper.toDto(racfGroup);

        restRacfGroupMockMvc.perform(post("/api/racf-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfGroupDTO)))
            .andExpect(status().isBadRequest());

        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacfGroups() throws Exception {
        // Initialize the database
        racfGroupRepository.saveAndFlush(racfGroup);

        // Get all the racfGroupList
        restRacfGroupMockMvc.perform(get("/api/racf-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racfGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].gid").value(hasItem(DEFAULT_GID)));
    }
    
    @Test
    @Transactional
    public void getRacfGroup() throws Exception {
        // Initialize the database
        racfGroupRepository.saveAndFlush(racfGroup);

        // Get the racfGroup
        restRacfGroupMockMvc.perform(get("/api/racf-groups/{id}", racfGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racfGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.gid").value(DEFAULT_GID));
    }

    @Test
    @Transactional
    public void getNonExistingRacfGroup() throws Exception {
        // Get the racfGroup
        restRacfGroupMockMvc.perform(get("/api/racf-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacfGroup() throws Exception {
        // Initialize the database
        racfGroupRepository.saveAndFlush(racfGroup);

        int databaseSizeBeforeUpdate = racfGroupRepository.findAll().size();

        // Update the racfGroup
        RacfGroup updatedRacfGroup = racfGroupRepository.findById(racfGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRacfGroup are not directly saved in db
        em.detach(updatedRacfGroup);
        updatedRacfGroup
            .name(UPDATED_NAME)
            .gid(UPDATED_GID);
        RacfGroupDTO racfGroupDTO = racfGroupMapper.toDto(updatedRacfGroup);

        restRacfGroupMockMvc.perform(put("/api/racf-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfGroupDTO)))
            .andExpect(status().isOk());

        // Validate the RacfGroup in the database
        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeUpdate);
        RacfGroup testRacfGroup = racfGroupList.get(racfGroupList.size() - 1);
        assertThat(testRacfGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRacfGroup.getGid()).isEqualTo(UPDATED_GID);

        // Validate the RacfGroup in Elasticsearch
        verify(mockRacfGroupSearchRepository, times(1)).save(testRacfGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingRacfGroup() throws Exception {
        int databaseSizeBeforeUpdate = racfGroupRepository.findAll().size();

        // Create the RacfGroup
        RacfGroupDTO racfGroupDTO = racfGroupMapper.toDto(racfGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacfGroupMockMvc.perform(put("/api/racf-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacfGroup in the database
        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RacfGroup in Elasticsearch
        verify(mockRacfGroupSearchRepository, times(0)).save(racfGroup);
    }

    @Test
    @Transactional
    public void deleteRacfGroup() throws Exception {
        // Initialize the database
        racfGroupRepository.saveAndFlush(racfGroup);

        int databaseSizeBeforeDelete = racfGroupRepository.findAll().size();

        // Delete the racfGroup
        restRacfGroupMockMvc.perform(delete("/api/racf-groups/{id}", racfGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RacfGroup> racfGroupList = racfGroupRepository.findAll();
        assertThat(racfGroupList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RacfGroup in Elasticsearch
        verify(mockRacfGroupSearchRepository, times(1)).deleteById(racfGroup.getId());
    }

    @Test
    @Transactional
    public void searchRacfGroup() throws Exception {
        // Initialize the database
        racfGroupRepository.saveAndFlush(racfGroup);
        when(mockRacfGroupSearchRepository.search(queryStringQuery("id:" + racfGroup.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(racfGroup), PageRequest.of(0, 1), 1));
        // Search the racfGroup
        restRacfGroupMockMvc.perform(get("/api/_search/racf-groups?query=id:" + racfGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racfGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gid").value(hasItem(DEFAULT_GID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacfGroup.class);
        RacfGroup racfGroup1 = new RacfGroup();
        racfGroup1.setId(1L);
        RacfGroup racfGroup2 = new RacfGroup();
        racfGroup2.setId(racfGroup1.getId());
        assertThat(racfGroup1).isEqualTo(racfGroup2);
        racfGroup2.setId(2L);
        assertThat(racfGroup1).isNotEqualTo(racfGroup2);
        racfGroup1.setId(null);
        assertThat(racfGroup1).isNotEqualTo(racfGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacfGroupDTO.class);
        RacfGroupDTO racfGroupDTO1 = new RacfGroupDTO();
        racfGroupDTO1.setId(1L);
        RacfGroupDTO racfGroupDTO2 = new RacfGroupDTO();
        assertThat(racfGroupDTO1).isNotEqualTo(racfGroupDTO2);
        racfGroupDTO2.setId(racfGroupDTO1.getId());
        assertThat(racfGroupDTO1).isEqualTo(racfGroupDTO2);
        racfGroupDTO2.setId(2L);
        assertThat(racfGroupDTO1).isNotEqualTo(racfGroupDTO2);
        racfGroupDTO1.setId(null);
        assertThat(racfGroupDTO1).isNotEqualTo(racfGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(racfGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(racfGroupMapper.fromId(null)).isNull();
    }
}
