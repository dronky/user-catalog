package com.zos.usercatalog.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SystemSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SystemSearchRepositoryMockConfiguration {

    @MockBean
    private SystemSearchRepository mockSystemSearchRepository;

}
