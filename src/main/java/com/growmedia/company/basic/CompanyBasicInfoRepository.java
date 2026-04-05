package com.growmedia.company.basic;

import com.growmedia.client.NotionApiClient;
import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyBasicInfoRepository {

    private final NotionApiClient notionApiClient;
    private final NotionResponseParser parser;
    private final CompanyBasicInfoMapper mapper;

    @Value("${notion.db.company.basic-id}")
    private String databaseId;

    @Cacheable("company-basic")
    public CompanyBasicInfo findAll() {
        List<JsonNode> jsonNodes = parser.parseResults(notionApiClient.queryDatabase(databaseId));
        return mapper.toCompanyBasicInfo(jsonNodes);
    }

    @CacheEvict(value = "company-basic", allEntries = true)
    @Scheduled(fixedRateString = "${notion.cache.ttl-ms:3600000}") // 기본 1시간
    public void evictCache() {}
}
