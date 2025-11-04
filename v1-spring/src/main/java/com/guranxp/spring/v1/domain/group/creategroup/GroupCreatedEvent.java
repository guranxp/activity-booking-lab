package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.BaseEvent;

import java.util.Objects;
import java.util.UUID;

public class GroupCreatedEvent extends BaseEvent {
    private final String aggregateId;
    private final String groupName;

    public GroupCreatedEvent(String aggregateId, String groupName) {
        this(UUID.randomUUID().toString(), aggregateId, groupName);
    }

    public GroupCreatedEvent(String id, String aggregateId, String groupName) {
        super(id);
        this.aggregateId = aggregateId;
        this.groupName = groupName;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroupCreatedEvent that = (GroupCreatedEvent) o;
        return Objects.equals(aggregateId, that.aggregateId) && Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aggregateId, groupName);
    }
}
