package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;

public record EventAlreadyScheduledEvent(String aggregateId, String eventName) implements Event {
}
