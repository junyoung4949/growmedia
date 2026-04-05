package com.growmedia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceQueryService {

    private final ServiceRepository repository;

    public List<ServiceItem> getVisibleService() {
        return repository.findAll()
                .stream()
                .filter(ServiceItem::getVisible)
                .sorted(Comparator.comparingInt(ServiceItem::getOrder))
                .toList();
    }
}
