package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.ZosSystem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ZosSystem} entity.
 */
public interface ZosSystemSearchRepository extends ElasticsearchRepository<ZosSystem, Long> {
}
