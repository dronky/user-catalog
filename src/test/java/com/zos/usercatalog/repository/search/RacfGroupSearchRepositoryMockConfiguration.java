package com.zos.usercatalog.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link RacfGroupSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class RacfGroupSearchRepositoryMockConfiguration {

    @MockBean
    private RacfGroupSearchRepository mockRacfGroupSearchRepository;

}
