package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.Arm;
import com.zos.usercatalog.repository.ArmRepository;
import com.zos.usercatalog.repository.search.ArmSearchRepository;
import com.zos.usercatalog.service.ArmService;
import com.zos.usercatalog.service.dto.ArmDTO;
import com.zos.usercatalog.service.mapper.ArmMapper;
import com.zos.usercatalog.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Integration tests for the {@Link ArmResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class ArmResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ArmRepository armRepository;

    @Mock
    private ArmRepository armRepositoryMock;

    @Autowired
    private ArmMapper armMapper;

    @Mock
    private ArmService armServiceMock;

    @Autowired
    private ArmService armService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.ArmSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArmSearchRepository mockArmSearchRepository;

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

    private MockMvc restArmMockMvc;

    private Arm arm;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArmResource armResource = new ArmResource(armService);
        this.restArmMockMvc = MockMvcBuilders.standaloneSetup(armResource)
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
    public static Arm createEntity(EntityManager em) {
        Arm arm = new Arm()
            .name(DEFAULT_NAME);
        return arm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arm createUpdatedEntity(EntityManager em) {
        Arm arm = new Arm()
            .name(UPDATED_NAME);
        return arm;
    }

    @BeforeEach
    public void initTest() {
        arm = createEntity(em);
    }

    @Test
    @Transactional
    public void createArm() throws Exception {
        int databaseSizeBeforeCreate = armRepository.findAll().size();

        // Create the Arm
        ArmDTO armDTO = armMapper.toDto(arm);
        restArmMockMvc.perform(post("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armDTO)))
            .andExpect(status().isCreated());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeCreate + 1);
        Arm testArm = armList.get(armList.size() - 1);
        assertThat(testArm.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Arm in Elasticsearch
        verify(mockArmSearchRepository, times(1)).save(testArm);
    }

    @Test
    @Transactional
    public void createArmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armRepository.findAll().size();

        // Create the Arm with an existing ID
        arm.setId(1L);
        ArmDTO armDTO = armMapper.toDto(arm);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmMockMvc.perform(post("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeCreate);

        // Validate the Arm in Elasticsearch
        verify(mockArmSearchRepository, times(0)).save(arm);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = armRepository.findAll().size();
        // set the field null
        arm.setName(null);

        // Create the Arm, which fails.
        ArmDTO armDTO = armMapper.toDto(arm);

        restArmMockMvc.perform(post("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armDTO)))
            .andExpect(status().isBadRequest());

        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArms() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        // Get all the armList
        restArmMockMvc.perform(get("/api/arms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArmsWithEagerRelationshipsIsEnabled() throws Exception {
        ArmResource armResource = new ArmResource(armServiceMock);
        when(armServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArmMockMvc = MockMvcBuilders.standaloneSetup(armResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArmMockMvc.perform(get("/api/arms?eagerload=true"))
        .andExpect(status().isOk());

        verify(armServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArmsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArmResource armResource = new ArmResource(armServiceMock);
            when(armServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArmMockMvc = MockMvcBuilders.standaloneSetup(armResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArmMockMvc.perform(get("/api/arms?eagerload=true"))
        .andExpect(status().isOk());

            verify(armServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        // Get the arm
        restArmMockMvc.perform(get("/api/arms/{id}", arm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(arm.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArm() throws Exception {
        // Get the arm
        restArmMockMvc.perform(get("/api/arms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        int databaseSizeBeforeUpdate = armRepository.findAll().size();

        // Update the arm
        Arm updatedArm = armRepository.findById(arm.getId()).get();
        // Disconnect from session so that the updates on updatedArm are not directly saved in db
        em.detach(updatedArm);
        updatedArm
            .name(UPDATED_NAME);
        ArmDTO armDTO = armMapper.toDto(updatedArm);

        restArmMockMvc.perform(put("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armDTO)))
            .andExpect(status().isOk());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeUpdate);
        Arm testArm = armList.get(armList.size() - 1);
        assertThat(testArm.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Arm in Elasticsearch
        verify(mockArmSearchRepository, times(1)).save(testArm);
    }

    @Test
    @Transactional
    public void updateNonExistingArm() throws Exception {
        int databaseSizeBeforeUpdate = armRepository.findAll().size();

        // Create the Arm
        ArmDTO armDTO = armMapper.toDto(arm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmMockMvc.perform(put("/api/arms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Arm in the database
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arm in Elasticsearch
        verify(mockArmSearchRepository, times(0)).save(arm);
    }

    @Test
    @Transactional
    public void deleteArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);

        int databaseSizeBeforeDelete = armRepository.findAll().size();

        // Delete the arm
        restArmMockMvc.perform(delete("/api/arms/{id}", arm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Arm> armList = armRepository.findAll();
        assertThat(armList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Arm in Elasticsearch
        verify(mockArmSearchRepository, times(1)).deleteById(arm.getId());
    }

    @Test
    @Transactional
    public void searchArm() throws Exception {
        // Initialize the database
        armRepository.saveAndFlush(arm);
        when(mockArmSearchRepository.search(queryStringQuery("id:" + arm.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(arm), PageRequest.of(0, 1), 1));
        // Search the arm
        restArmMockMvc.perform(get("/api/_search/arms?query=id:" + arm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arm.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arm.class);
        Arm arm1 = new Arm();
        arm1.setId(1L);
        Arm arm2 = new Arm();
        arm2.setId(arm1.getId());
        assertThat(arm1).isEqualTo(arm2);
        arm2.setId(2L);
        assertThat(arm1).isNotEqualTo(arm2);
        arm1.setId(null);
        assertThat(arm1).isNotEqualTo(arm2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmDTO.class);
        ArmDTO armDTO1 = new ArmDTO();
        armDTO1.setId(1L);
        ArmDTO armDTO2 = new ArmDTO();
        assertThat(armDTO1).isNotEqualTo(armDTO2);
        armDTO2.setId(armDTO1.getId());
        assertThat(armDTO1).isEqualTo(armDTO2);
        armDTO2.setId(2L);
        assertThat(armDTO1).isNotEqualTo(armDTO2);
        armDTO1.setId(null);
        assertThat(armDTO1).isNotEqualTo(armDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(armMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(armMapper.fromId(null)).isNull();
    }
}
