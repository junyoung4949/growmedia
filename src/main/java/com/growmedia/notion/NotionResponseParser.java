package com.growmedia.notion;

import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotionResponseParser {

    /**
     * queryDatabase 응답에서 results 배열 추출
     */
    public List<JsonNode> parseResults(JsonNode response) {
        List<JsonNode> results = new ArrayList<>();
        JsonNode resultsNode = response.path("results");

        if (resultsNode.isArray()) {
            resultsNode.forEach(results::add);
        }

        return results;
    }

    /**
     * 텍스트 타입 프로퍼티 추출 (rich_text, title)
     * ex) properties.서비스명.title[0].plain_text
     */
    public String extractText(JsonNode properties, String propertyName) {
        JsonNode property = properties.path(propertyName);
        String type = property.path("type").asText();

        JsonNode textArray = property.path(type);
        if (textArray.isArray() && !textArray.isEmpty()) {
            return textArray.get(0).path("plain_text").asText("");
        }

        return "";
    }

    /**
     * 숫자 타입 프로퍼티 추출
     * ex) properties.숫자.number
     */
    public int extractNumber(JsonNode properties, String propertyName) {
        return properties.path(propertyName).path("number").asInt(0);
    }

    /**
     * 체크박스 타입 프로퍼티 추출
     * ex) properties.공개여부.checkbox
     */
    public boolean extractCheckbox(JsonNode properties, String propertyName) {
        return properties.path(propertyName).path("checkbox").asBoolean(false);
    }

    /**
     * select 타입 프로퍼티 추출
     * ex) properties.카테고리.select.name
     */
    public String extractSelect(JsonNode properties, String propertyName) {
        return properties.path(propertyName).path("select").path("name").asText("");
    }
}
