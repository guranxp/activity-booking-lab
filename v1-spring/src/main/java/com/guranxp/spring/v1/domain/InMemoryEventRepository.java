package com.guranxp.spring.v1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;

public class InMemoryEventRepository implements EventRepository {

    private final Map<String, List<Event>> store = new ConcurrentHashMap<>();

    @Override
    public List<Event> loadEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId) {
        return store.getOrDefault(aggregateId, emptyList());
    }

    @Override
    public void saveEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId, List<Event> events) {
        store.computeIfAbsent(aggregateId, id -> new ArrayList<>()).addAll(events);
    }

}
