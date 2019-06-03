package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.Request;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Request} entity.
 */
public interface RequestSearchRepository extends ElasticsearchRepository<Request, Long> {
}
