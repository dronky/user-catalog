package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.RacfGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RacfGroup} entity.
 */
public interface RacfGroupSearchRepository extends ElasticsearchRepository<RacfGroup, Long> {
}
