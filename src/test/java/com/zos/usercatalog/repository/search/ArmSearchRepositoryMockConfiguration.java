package com.zos.usercatalog.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ArmSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ArmSearchRepositoryMockConfiguration {

    @MockBean
    private ArmSearchRepository mockArmSearchRepository;

}
