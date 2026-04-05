package com.growmedia.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsQueryService {

    private final StatsRepository repository;

    public List<StatItem> getVisibleStats() {
        return repository.findAll()
                .stream()
                .filter(StatItem::getVisible)
                .sorted(Comparator.comparingInt(StatItem::getOrder))
                .toList();
    }
}
