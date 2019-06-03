package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.Owner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Owner} entity.
 */
public interface OwnerSearchRepository extends ElasticsearchRepository<Owner, Long> {
}
