package com.growmedia.portfolio;

import lombok.Getter;

@Getter
public class PortfolioItem {
    private final Boolean visible;
    private final Integer order;
    private final String emoji;
    private final String category;
    private final String title;
    private final String resultPrefix;
    private final String resultHighlight;
    private final String resultSuffix;

    public PortfolioItem(Boolean visible, Integer order, String emoji, String category, String title, String resultPrefix, String resultHighlight, String resultSuffix) {
        this.visible = visible == null? Boolean.FALSE : visible;
        this.order = order == null? Integer.MAX_VALUE : order;
        this.emoji = emoji == null? "❔" : emoji;
        this.category = category == null? "빈카테고리" : category;
        this.title = title == null? "제목없음" : title;
        this.resultPrefix = resultPrefix == null? "접두사" : resultPrefix;
        this.resultHighlight = resultHighlight == null? "하이라이트" : resultHighlight;
        this.resultSuffix = resultSuffix == null? "접미사" : resultSuffix;
    }
}
