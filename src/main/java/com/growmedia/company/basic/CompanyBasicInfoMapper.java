package com.growmedia.company.basic;

import com.growmedia.notion.NotionResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyBasicInfoMapper {

    private final NotionResponseParser parser;

    public CompanyBasicInfo toCompanyBasicInfo(List<JsonNode> jsonNodes) {
        Map<String, String> map = jsonNodes.stream()
                .collect(Collectors.toMap(
                        node -> parser.extractText(node.path("properties"), "항목"),
                        node -> parser.extractText(node.path("properties"), "내용")
                ));

        return new CompanyBasicInfo(
                map.get("회사명"),
                map.get("대표자명"),
                map.get("개인정보보호책임자"),
                map.get("사업자등록번호"),
                map.get("주소"),
                map.get("이메일")
        );
    }
}
