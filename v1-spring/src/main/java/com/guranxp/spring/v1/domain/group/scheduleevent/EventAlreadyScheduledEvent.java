package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.BaseEvent;

import java.util.Objects;
import java.util.UUID;

public class EventAlreadyScheduledEvent extends BaseEvent {
    private final String aggregateId;
    private final String eventName;

    public EventAlreadyScheduledEvent(String aggregateId, String eventName) {
        this(UUID.randomUUID().toString(), aggregateId, eventName);
    }

    public EventAlreadyScheduledEvent(String id, String aggregateId, String eventName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EventAlreadyScheduledEvent that = (EventAlreadyScheduledEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) && Objects.equals(eventName, that.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aggregateId, eventName);
    }
}
