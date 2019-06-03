package com.zos.usercatalog.repository.search;

import com.zos.usercatalog.domain.ArmIp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ArmIp} entity.
 */
public interface ArmIpSearchRepository extends ElasticsearchRepository<ArmIp, Long> {
}
