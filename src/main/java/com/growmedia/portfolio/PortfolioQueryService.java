package com.growmedia.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioQueryService {

    private final PortfolioRepository repository;

    public List<PortfolioItem> getVisiblePortfolio() {
        return repository.findAll()
                .stream()
                .filter(PortfolioItem::getVisible)
                .sorted(Comparator.comparingInt(PortfolioItem::getOrder))
                .toList();
    }
}
