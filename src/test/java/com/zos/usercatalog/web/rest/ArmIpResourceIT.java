package com.zos.usercatalog.web.rest;

import com.zos.usercatalog.UserCatalogApp;
import com.zos.usercatalog.domain.ArmIp;
import com.zos.usercatalog.repository.ArmIpRepository;
import com.zos.usercatalog.repository.search.ArmIpSearchRepository;
import com.zos.usercatalog.service.ArmIpService;
import com.zos.usercatalog.service.dto.ArmIpDTO;
import com.zos.usercatalog.service.mapper.ArmIpMapper;
import com.zos.usercatalog.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import com.zos.usercatalog.domain.enumeration.Type;
import com.zos.usercatalog.domain.enumeration.IpVersion;
/**
 * Integration tests for the {@Link ArmIpResource} REST controller.
 */
@SpringBootTest(classes = UserCatalogApp.class)
public class ArmIpResourceIT {

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final Type DEFAULT_TYPE = Type.TEST_DEV;
    private static final Type UPDATED_TYPE = Type.PRODUCTION;

    private static final IpVersion DEFAULT_IP_VERSION = IpVersion.IPV4;
    private static final IpVersion UPDATED_IP_VERSION = IpVersion.IPV6;

    @Autowired
    private ArmIpRepository armIpRepository;

    @Autowired
    private ArmIpMapper armIpMapper;

    @Autowired
    private ArmIpService armIpService;

    /**
     * This repository is mocked in the com.zos.usercatalog.repository.search test package.
     *
     * @see com.zos.usercatalog.repository.search.ArmIpSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArmIpSearchRepository mockArmIpSearchRepository;

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

    private MockMvc restArmIpMockMvc;

    private ArmIp armIp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArmIpResource armIpResource = new ArmIpResource(armIpService);
        this.restArmIpMockMvc = MockMvcBuilders.standaloneSetup(armIpResource)
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
    public static ArmIp createEntity(EntityManager em) {
        ArmIp armIp = new ArmIp()
            .ip(DEFAULT_IP)
            .type(DEFAULT_TYPE)
            .ipVersion(DEFAULT_IP_VERSION);
        return armIp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmIp createUpdatedEntity(EntityManager em) {
        ArmIp armIp = new ArmIp()
            .ip(UPDATED_IP)
            .type(UPDATED_TYPE)
            .ipVersion(UPDATED_IP_VERSION);
        return armIp;
    }

    @BeforeEach
    public void initTest() {
        armIp = createEntity(em);
    }

    @Test
    @Transactional
    public void createArmIp() throws Exception {
        int databaseSizeBeforeCreate = armIpRepository.findAll().size();

        // Create the ArmIp
        ArmIpDTO armIpDTO = armIpMapper.toDto(armIp);
        restArmIpMockMvc.perform(post("/api/arm-ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armIpDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmIp in the database
        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeCreate + 1);
        ArmIp testArmIp = armIpList.get(armIpList.size() - 1);
        assertThat(testArmIp.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testArmIp.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArmIp.getIpVersion()).isEqualTo(DEFAULT_IP_VERSION);

        // Validate the ArmIp in Elasticsearch
        verify(mockArmIpSearchRepository, times(1)).save(testArmIp);
    }

    @Test
    @Transactional
    public void createArmIpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armIpRepository.findAll().size();

        // Create the ArmIp with an existing ID
        armIp.setId(1L);
        ArmIpDTO armIpDTO = armIpMapper.toDto(armIp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmIpMockMvc.perform(post("/api/arm-ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armIpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmIp in the database
        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArmIp in Elasticsearch
        verify(mockArmIpSearchRepository, times(0)).save(armIp);
    }


    @Test
    @Transactional
    public void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = armIpRepository.findAll().size();
        // set the field null
        armIp.setIp(null);

        // Create the ArmIp, which fails.
        ArmIpDTO armIpDTO = armIpMapper.toDto(armIp);

        restArmIpMockMvc.perform(post("/api/arm-ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armIpDTO)))
            .andExpect(status().isBadRequest());

        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArmIps() throws Exception {
        // Initialize the database
        armIpRepository.saveAndFlush(armIp);

        // Get all the armIpList
        restArmIpMockMvc.perform(get("/api/arm-ips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armIp.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ipVersion").value(hasItem(DEFAULT_IP_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getArmIp() throws Exception {
        // Initialize the database
        armIpRepository.saveAndFlush(armIp);

        // Get the armIp
        restArmIpMockMvc.perform(get("/api/arm-ips/{id}", armIp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(armIp.getId().intValue()))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.ipVersion").value(DEFAULT_IP_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArmIp() throws Exception {
        // Get the armIp
        restArmIpMockMvc.perform(get("/api/arm-ips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArmIp() throws Exception {
        // Initialize the database
        armIpRepository.saveAndFlush(armIp);

        int databaseSizeBeforeUpdate = armIpRepository.findAll().size();

        // Update the armIp
        ArmIp updatedArmIp = armIpRepository.findById(armIp.getId()).get();
        // Disconnect from session so that the updates on updatedArmIp are not directly saved in db
        em.detach(updatedArmIp);
        updatedArmIp
            .ip(UPDATED_IP)
            .type(UPDATED_TYPE)
            .ipVersion(UPDATED_IP_VERSION);
        ArmIpDTO armIpDTO = armIpMapper.toDto(updatedArmIp);

        restArmIpMockMvc.perform(put("/api/arm-ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armIpDTO)))
            .andExpect(status().isOk());

        // Validate the ArmIp in the database
        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeUpdate);
        ArmIp testArmIp = armIpList.get(armIpList.size() - 1);
        assertThat(testArmIp.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testArmIp.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArmIp.getIpVersion()).isEqualTo(UPDATED_IP_VERSION);

        // Validate the ArmIp in Elasticsearch
        verify(mockArmIpSearchRepository, times(1)).save(testArmIp);
    }

    @Test
    @Transactional
    public void updateNonExistingArmIp() throws Exception {
        int databaseSizeBeforeUpdate = armIpRepository.findAll().size();

        // Create the ArmIp
        ArmIpDTO armIpDTO = armIpMapper.toDto(armIp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmIpMockMvc.perform(put("/api/arm-ips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(armIpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmIp in the database
        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArmIp in Elasticsearch
        verify(mockArmIpSearchRepository, times(0)).save(armIp);
    }

    @Test
    @Transactional
    public void deleteArmIp() throws Exception {
        // Initialize the database
        armIpRepository.saveAndFlush(armIp);

        int databaseSizeBeforeDelete = armIpRepository.findAll().size();

        // Delete the armIp
        restArmIpMockMvc.perform(delete("/api/arm-ips/{id}", armIp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ArmIp> armIpList = armIpRepository.findAll();
        assertThat(armIpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArmIp in Elasticsearch
        verify(mockArmIpSearchRepository, times(1)).deleteById(armIp.getId());
    }

    @Test
    @Transactional
    public void searchArmIp() throws Exception {
        // Initialize the database
        armIpRepository.saveAndFlush(armIp);
        when(mockArmIpSearchRepository.search(queryStringQuery("id:" + armIp.getId())))
            .thenReturn(Collections.singletonList(armIp));
        // Search the armIp
        restArmIpMockMvc.perform(get("/api/_search/arm-ips?query=id:" + armIp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armIp.getId().intValue())))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ipVersion").value(hasItem(DEFAULT_IP_VERSION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmIp.class);
        ArmIp armIp1 = new ArmIp();
        armIp1.setId(1L);
        ArmIp armIp2 = new ArmIp();
        armIp2.setId(armIp1.getId());
        assertThat(armIp1).isEqualTo(armIp2);
        armIp2.setId(2L);
        assertThat(armIp1).isNotEqualTo(armIp2);
        armIp1.setId(null);
        assertThat(armIp1).isNotEqualTo(armIp2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmIpDTO.class);
        ArmIpDTO armIpDTO1 = new ArmIpDTO();
        armIpDTO1.setId(1L);
        ArmIpDTO armIpDTO2 = new ArmIpDTO();
        assertThat(armIpDTO1).isNotEqualTo(armIpDTO2);
        armIpDTO2.setId(armIpDTO1.getId());
        assertThat(armIpDTO1).isEqualTo(armIpDTO2);
        armIpDTO2.setId(2L);
        assertThat(armIpDTO1).isNotEqualTo(armIpDTO2);
        armIpDTO1.setId(null);
        assertThat(armIpDTO1).isNotEqualTo(armIpDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(armIpMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(armIpMapper.fromId(null)).isNull();
    }
}
