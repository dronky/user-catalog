package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.RacfUser;
import com.zos.usercatalog.repository.RacfUserRepository;
import com.zos.usercatalog.repository.search.RacfUserSearchRepository;
import com.zos.usercatalog.service.RacfUserService;
import com.zos.usercatalog.service.dto.RacfUserDTO;
import com.zos.usercatalog.service.mapper.RacfUserMapper;
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
 * Integration tests for the {@Link RacfUserResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class RacfUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBB";

    private static final Integer DEFAULT_UID = 1;
    private static final Integer UPDATED_UID = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private RacfUserRepository racfUserRepository;

    @Mock
    private RacfUserRepository racfUserRepositoryMock;

    @Autowired
    private RacfUserMapper racfUserMapper;

    @Mock
    private RacfUserService racfUserServiceMock;

    @Autowired
    private RacfUserService racfUserService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.RacfUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private RacfUserSearchRepository mockRacfUserSearchRepository;

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

    private MockMvc restRacfUserMockMvc;

    private RacfUser racfUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacfUserResource racfUserResource = new RacfUserResource(racfUserService);
        this.restRacfUserMockMvc = MockMvcBuilders.standaloneSetup(racfUserResource)
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
    public static RacfUser createEntity(EntityManager em) {
        RacfUser racfUser = new RacfUser()
            .name(DEFAULT_NAME)
            .uid(DEFAULT_UID)
            .type(DEFAULT_TYPE);
        return racfUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RacfUser createUpdatedEntity(EntityManager em) {
        RacfUser racfUser = new RacfUser()
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .type(UPDATED_TYPE);
        return racfUser;
    }

    @BeforeEach
    public void initTest() {
        racfUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacfUser() throws Exception {
        int databaseSizeBeforeCreate = racfUserRepository.findAll().size();

        // Create the RacfUser
        RacfUserDTO racfUserDTO = racfUserMapper.toDto(racfUser);
        restRacfUserMockMvc.perform(post("/api/racf-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfUserDTO)))
            .andExpect(status().isCreated());

        // Validate the RacfUser in the database
        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeCreate + 1);
        RacfUser testRacfUser = racfUserList.get(racfUserList.size() - 1);
        assertThat(testRacfUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRacfUser.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testRacfUser.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the RacfUser in Elasticsearch
        verify(mockRacfUserSearchRepository, times(1)).save(testRacfUser);
    }

    @Test
    @Transactional
    public void createRacfUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racfUserRepository.findAll().size();

        // Create the RacfUser with an existing ID
        racfUser.setId(1L);
        RacfUserDTO racfUserDTO = racfUserMapper.toDto(racfUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacfUserMockMvc.perform(post("/api/racf-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacfUser in the database
        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the RacfUser in Elasticsearch
        verify(mockRacfUserSearchRepository, times(0)).save(racfUser);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = racfUserRepository.findAll().size();
        // set the field null
        racfUser.setName(null);

        // Create the RacfUser, which fails.
        RacfUserDTO racfUserDTO = racfUserMapper.toDto(racfUser);

        restRacfUserMockMvc.perform(post("/api/racf-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfUserDTO)))
            .andExpect(status().isBadRequest());

        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRacfUsers() throws Exception {
        // Initialize the database
        racfUserRepository.saveAndFlush(racfUser);

        // Get all the racfUserList
        restRacfUserMockMvc.perform(get("/api/racf-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racfUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRacfUsersWithEagerRelationshipsIsEnabled() throws Exception {
        RacfUserResource racfUserResource = new RacfUserResource(racfUserServiceMock);
        when(racfUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRacfUserMockMvc = MockMvcBuilders.standaloneSetup(racfUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRacfUserMockMvc.perform(get("/api/racf-users?eagerload=true"))
        .andExpect(status().isOk());

        verify(racfUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRacfUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        RacfUserResource racfUserResource = new RacfUserResource(racfUserServiceMock);
            when(racfUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRacfUserMockMvc = MockMvcBuilders.standaloneSetup(racfUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRacfUserMockMvc.perform(get("/api/racf-users?eagerload=true"))
        .andExpect(status().isOk());

            verify(racfUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRacfUser() throws Exception {
        // Initialize the database
        racfUserRepository.saveAndFlush(racfUser);

        // Get the racfUser
        restRacfUserMockMvc.perform(get("/api/racf-users/{id}", racfUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racfUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRacfUser() throws Exception {
        // Get the racfUser
        restRacfUserMockMvc.perform(get("/api/racf-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacfUser() throws Exception {
        // Initialize the database
        racfUserRepository.saveAndFlush(racfUser);

        int databaseSizeBeforeUpdate = racfUserRepository.findAll().size();

        // Update the racfUser
        RacfUser updatedRacfUser = racfUserRepository.findById(racfUser.getId()).get();
        // Disconnect from session so that the updates on updatedRacfUser are not directly saved in db
        em.detach(updatedRacfUser);
        updatedRacfUser
            .name(UPDATED_NAME)
            .uid(UPDATED_UID)
            .type(UPDATED_TYPE);
        RacfUserDTO racfUserDTO = racfUserMapper.toDto(updatedRacfUser);

        restRacfUserMockMvc.perform(put("/api/racf-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfUserDTO)))
            .andExpect(status().isOk());

        // Validate the RacfUser in the database
        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeUpdate);
        RacfUser testRacfUser = racfUserList.get(racfUserList.size() - 1);
        assertThat(testRacfUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRacfUser.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testRacfUser.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the RacfUser in Elasticsearch
        verify(mockRacfUserSearchRepository, times(1)).save(testRacfUser);
    }

    @Test
    @Transactional
    public void updateNonExistingRacfUser() throws Exception {
        int databaseSizeBeforeUpdate = racfUserRepository.findAll().size();

        // Create the RacfUser
        RacfUserDTO racfUserDTO = racfUserMapper.toDto(racfUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRacfUserMockMvc.perform(put("/api/racf-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racfUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RacfUser in the database
        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the RacfUser in Elasticsearch
        verify(mockRacfUserSearchRepository, times(0)).save(racfUser);
    }

    @Test
    @Transactional
    public void deleteRacfUser() throws Exception {
        // Initialize the database
        racfUserRepository.saveAndFlush(racfUser);

        int databaseSizeBeforeDelete = racfUserRepository.findAll().size();

        // Delete the racfUser
        restRacfUserMockMvc.perform(delete("/api/racf-users/{id}", racfUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RacfUser> racfUserList = racfUserRepository.findAll();
        assertThat(racfUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the RacfUser in Elasticsearch
        verify(mockRacfUserSearchRepository, times(1)).deleteById(racfUser.getId());
    }

    @Test
    @Transactional
    public void searchRacfUser() throws Exception {
        // Initialize the database
        racfUserRepository.saveAndFlush(racfUser);
        when(mockRacfUserSearchRepository.search(queryStringQuery("id:" + racfUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(racfUser), PageRequest.of(0, 1), 1));
        // Search the racfUser
        restRacfUserMockMvc.perform(get("/api/_search/racf-users?query=id:" + racfUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racfUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacfUser.class);
        RacfUser racfUser1 = new RacfUser();
        racfUser1.setId(1L);
        RacfUser racfUser2 = new RacfUser();
        racfUser2.setId(racfUser1.getId());
        assertThat(racfUser1).isEqualTo(racfUser2);
        racfUser2.setId(2L);
        assertThat(racfUser1).isNotEqualTo(racfUser2);
        racfUser1.setId(null);
        assertThat(racfUser1).isNotEqualTo(racfUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacfUserDTO.class);
        RacfUserDTO racfUserDTO1 = new RacfUserDTO();
        racfUserDTO1.setId(1L);
        RacfUserDTO racfUserDTO2 = new RacfUserDTO();
        assertThat(racfUserDTO1).isNotEqualTo(racfUserDTO2);
        racfUserDTO2.setId(racfUserDTO1.getId());
        assertThat(racfUserDTO1).isEqualTo(racfUserDTO2);
        racfUserDTO2.setId(2L);
        assertThat(racfUserDTO1).isNotEqualTo(racfUserDTO2);
        racfUserDTO1.setId(null);
        assertThat(racfUserDTO1).isNotEqualTo(racfUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(racfUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(racfUserMapper.fromId(null)).isNull();
    }
}
