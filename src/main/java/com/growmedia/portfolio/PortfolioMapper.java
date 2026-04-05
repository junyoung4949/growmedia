package com.growmedia.portfolio;

import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PortfolioMapper {

    private final NotionResponseParser parser;

    public List<PortfolioItem> toPortfolioList(List<JsonNode> jsonNodes) {
        return jsonNodes.stream().map(jsonNode -> {
            JsonNode prop = jsonNode.path("properties");

            boolean visible = parser.extractCheckbox(prop, "공개 여부");
            int order = parser.extractNumber(prop, "순서");

            String emoji = parser.extractText(prop, "이모지");
            String category = parser.extractSelect(prop, "카테고리");
            String title = parser.extractText(prop, "프로젝트명");

            String resultPrefix = parser.extractText(prop, "성과 접두어");
            String resultHighlight = parser.extractText(prop, "성과 수치 (하이라이트)");
            String resultSuffix = parser.extractText(prop, "성과 접미어");

            return new PortfolioItem(visible, order, emoji, category, title, resultPrefix, resultHighlight, resultSuffix);
        }).toList();
    }
}
