package com.growmedia.stats;

import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatItemMapper {

    private final NotionResponseParser parser;

    public List<StatItem> toStatList(List<JsonNode> jsonNodes) {
        return jsonNodes.stream().map(jsonNode -> {
            JsonNode prop = jsonNode.path("properties");

            Boolean visible = parser.extractCheckbox(prop, "공개 여부");
            Integer order = parser.extractNumber(prop, "순서");

            String label = parser.extractText(prop, "라벨");
            Integer value = parser.extractNumber(prop, "숫자");
            String unit = parser.extractSelect(prop, "단위");

            return new StatItem(order, visible, label, value, unit);
        }).toList();
    }
}
