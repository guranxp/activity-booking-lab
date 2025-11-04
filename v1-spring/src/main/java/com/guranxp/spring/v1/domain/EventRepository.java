package com.guranxp.spring.v1.domain;

import java.util.List;

public interface EventRepository {

    List<Event> loadEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId);

    void saveEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId, List<Event> events);
}
