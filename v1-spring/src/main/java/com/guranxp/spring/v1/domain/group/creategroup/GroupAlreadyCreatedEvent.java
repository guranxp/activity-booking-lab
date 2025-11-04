package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.BaseEvent;

import java.util.Objects;
import java.util.UUID;

public class GroupAlreadyCreatedEvent extends BaseEvent {
    private final String aggregateId;

    public GroupAlreadyCreatedEvent(String aggregateId) {
        this(UUID.randomUUID().toString(), aggregateId);
    }

    public GroupAlreadyCreatedEvent(String id, String aggregateId) {
        super(id);
        this.aggregateId = aggregateId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroupAlreadyCreatedEvent that = (GroupAlreadyCreatedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aggregateId);
    }
}
