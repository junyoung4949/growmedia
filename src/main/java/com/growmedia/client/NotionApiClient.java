package com.growmedia.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Notion REST API와 직접 통신하는 클래스.
 * 변경 이유: Notion API 스펙(엔드포인트, 인증방식, 버전)이 바뀔 때만 수정.
 */
@Component
public class NotionApiClient {

    private static final String BASE_URL = "https://api.notion.com";
    private static final String NOTION_VERSION = "2026-03-11";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${notion.api.secret}")
    private String apiSecret;

    public NotionApiClient(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = objectMapper;
    }

    /**
     * Notion 데이터베이스 쿼리 — 페이지 목록 반환
     */
    public JsonNode queryDatabase(String databaseId) {
        try {
            // 정렬 조건: 순서(order) 기준 오름차순
            ObjectNode body = objectMapper.createObjectNode();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/v1/data_sources/" + databaseId + "/query"))
                    .header("Authorization", "Bearer " + apiSecret)
                    .header("Notion-Version", NOTION_VERSION)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            return objectMapper.readTree(response.body());

        } catch (Exception e) {
            throw new NotionApiException("Notion DB 쿼리 실패: " + databaseId, e);
        }
    }
}