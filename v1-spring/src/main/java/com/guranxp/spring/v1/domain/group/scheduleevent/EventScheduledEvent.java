package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.BaseEvent;

import java.util.UUID;

public class EventScheduledEvent extends BaseEvent {
    private final String aggregateId;
    private final String eventName;

    public EventScheduledEvent(String aggregateId, String eventName) {
        this(UUID.randomUUID().toString(), aggregateId, eventName);
    }

    public EventScheduledEvent(String id, String aggregateId, String eventName) {
        super(id);
        this.aggregateId = aggregateId;
        this.eventName = eventName;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getEventName() {
        return eventName;
    }
}
