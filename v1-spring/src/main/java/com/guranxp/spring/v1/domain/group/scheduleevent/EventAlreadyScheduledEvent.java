package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;

public record EventAlreadyScheduledEvent(String groupId, String eventName) implements Event {
}
