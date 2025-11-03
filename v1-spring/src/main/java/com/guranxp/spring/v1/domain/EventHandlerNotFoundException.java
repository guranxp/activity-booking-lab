package com.guranxp.spring.v1.domain;

public class EventHandlerNotFoundException extends RuntimeException {
    public EventHandlerNotFoundException(Class<?> eventType, Class<?> aggregateType) {
        super("No @EventSourcingHandler method found for event " + eventType.getSimpleName() +
                " in aggregate " + aggregateType.getSimpleName());
    }
}