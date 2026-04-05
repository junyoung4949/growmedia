package com.growmedia.company.manager;

import com.growmedia.client.NotionApiClient;
import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CompanyManagerInfoRepository {

    private final NotionApiClient notionApiClient;
    private final NotionResponseParser parser;
    private final CompanyManagerInfoMapper mapper;

    @Value("${notion.db.company.manager-id}")
    private String databaseId;

    @Cacheable("company-manager")
    public CompanyManagerInfo findAll() {
        List<JsonNode> jsonNodes = parser.parseResults(notionApiClient.queryDatabase(databaseId));
        return mapper.toCompanyManagerInfo(jsonNodes);
    }

    @CacheEvict(value = "company-manager", allEntries = true)
    @Scheduled(fixedRateString = "${notion.cache.ttl-ms:3600000}") // 기본 1시간
    public void evictCache() {}
}
