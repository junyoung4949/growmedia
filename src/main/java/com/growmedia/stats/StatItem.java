package com.growmedia.stats;

import lombok.Getter;

@Getter
public class StatItem {
    private final Integer order;
    private final Boolean visible;
    private final String label;
    private final Integer value;
    private final String unit;

    public StatItem(Integer order, Boolean visible, String label, Integer value, String unit) {
        this.order = order == null? Integer.MAX_VALUE : order;
        this.visible = visible == null? Boolean.FALSE : visible;
        this.label = label == null? "비어있음" : label;
        this.value = value == null? 0 : value;
        this.unit = unit == null? "단위없음" : unit;
    }
}
