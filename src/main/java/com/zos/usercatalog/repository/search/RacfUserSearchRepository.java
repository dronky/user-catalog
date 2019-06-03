package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.RacfUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RacfUser} entity.
 */
public interface RacfUserSearchRepository extends ElasticsearchRepository<RacfUser, Long> {
}
