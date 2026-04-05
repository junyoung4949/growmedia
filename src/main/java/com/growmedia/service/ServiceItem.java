package com.growmedia.service;

import lombok.Getter;

@Getter
public class ServiceItem {
    private final Integer order;
    private final Boolean visible;

    private final String emoji;
    private final String name;
    private final String description;
    private final String tag;
    private final Boolean featured;

    public ServiceItem(Integer order, Boolean visible, String emoji, String name, String description, String tag, Boolean featured) {
        this.order = order == null? Integer.MAX_VALUE : order;
        this.visible = visible == null? Boolean.FALSE: visible;
        this.emoji = emoji == null? "❔" : emoji;
        this.name = name == null? "서비스명 없음" : name;
        this.description = description == null? "설명 없음" : description;
        this.tag = tag == null? "" : tag;
        this.featured = featured == null? Boolean.FALSE: featured;
    }
}
