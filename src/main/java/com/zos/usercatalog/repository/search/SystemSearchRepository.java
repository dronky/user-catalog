package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.System;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link System} entity.
 */
public interface SystemSearchRepository extends ElasticsearchRepository<System, Long> {
}
