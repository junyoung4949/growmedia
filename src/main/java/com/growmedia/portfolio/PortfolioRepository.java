package com.growmedia.portfolio;

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

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {

    private final NotionApiClient notionApiClient;
    private final NotionResponseParser parser;
    private final PortfolioMapper mapper;

    @Value("${notion.db.portfolio-id}")
    private String databaseId;

    @Cacheable("portfolio")
    public List<PortfolioItem> findAll() {
        List<JsonNode> jsonNodes = parser.parseResults(notionApiClient.queryDatabase(databaseId));
        return mapper.toPortfolioList(jsonNodes);
    }

    @CacheEvict(value = "portfolio", allEntries = true)
    @Scheduled(fixedRateString = "${notion.cache.ttl-ms:3600000}") // 기본 1시간
    public void evictCache() {}
}