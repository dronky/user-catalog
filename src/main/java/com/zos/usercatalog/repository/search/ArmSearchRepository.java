package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.Arm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Arm} entity.
 */
public interface ArmSearchRepository extends ElasticsearchRepository<Arm, Long> {
}
