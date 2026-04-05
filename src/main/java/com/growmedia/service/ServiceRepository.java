package com.growmedia.service;

import com.growmedia.client.NotionApiClient;
import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ServiceRepository {

    private final NotionApiClient notionApiClient;
    private final NotionResponseParser parser;
    private final ServiceMapper mapper;

    @Value("${notion.db.service-id}")
    private String databaseId;

    @Cacheable("service")
    public List<ServiceItem> findAll() {
        List<JsonNode> jsonNodes = parser.parseResults(notionApiClient.queryDatabase(databaseId));
        return mapper.toServiceItemList(jsonNodes);
    }

    @CacheEvict(value = "service", allEntries = true)
    @Scheduled(fixedRateString = "${notion.cache.ttl-ms:3600000}") // 기본 1시간
    public void evictCache() {}

}
