package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.Event;

public record GroupCreatedEvent(String aggregateId, String groupName) implements Event {
}
