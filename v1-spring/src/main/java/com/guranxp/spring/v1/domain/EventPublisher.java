package com.guranxp.spring.v1.domain;

import java.util.List;

public interface EventPublisher {
    void publish(List<Event> events);
}
