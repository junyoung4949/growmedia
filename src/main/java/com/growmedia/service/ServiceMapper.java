package com.growmedia.service;

import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceMapper {

    private final NotionResponseParser parser;

    public List<ServiceItem> toServiceItemList(List<JsonNode> jsonNodes) {
        return jsonNodes.stream().map(jsonNode -> {
            JsonNode prop = jsonNode.path("properties");

            Integer order = parser.extractNumber(prop, "순서");
            Boolean visible = parser.extractCheckbox(prop, "공개 여부");

            String emoji = parser.extractText(prop, "이모지");
            String name = parser.extractText(prop, "서비스명");
            String description = parser.extractText(prop, "설명");
            String tag = parser.extractText(prop, "태그");
            Boolean featured = parser.extractCheckbox(prop, "인기 서비스");

            return new ServiceItem(order, visible, emoji, name, description, tag, featured);
        }).toList();
    }
}
