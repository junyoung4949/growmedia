package com.growmedia.company.manager;

import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyManagerInfoMapper {

    private final NotionResponseParser parser;

    private static final String KEY_TITLE    = "직함";
    private static final String KEY_NAME     = "이름";
    private static final String KEY_PHONE    = "연락처";
    private static final String KEY_EMAIL    = "이메일";

    public CompanyManagerInfo toCompanyManagerInfo(List<JsonNode> jsonNodes) {
        Map<String, String> map = jsonNodes.stream()
                .collect(Collectors.toMap(
                        node -> parser.extractText(node.path("properties"), "항목"),
                        node -> parser.extractText(node.path("properties"), "내용")
                ));

        return new CompanyManagerInfo(
                map.get(KEY_TITLE),
                map.get(KEY_NAME),
                map.get(KEY_PHONE),
                map.get(KEY_EMAIL)
        );
    }
}
