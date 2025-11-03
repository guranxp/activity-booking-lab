package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;

public record EventSchedulingFailedEvent(String groupId) implements Event {
}
