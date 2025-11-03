package com.guranxp.spring.v1.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DefaultCommandBus implements CommandBus {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    public DefaultCommandBus(EventRepository eventRepository, EventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public <T extends Aggregate<T>> CompletableFuture<CommandResult> dispatch(Command<T> command) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final List<Event> pastEvents = eventRepository.loadEvents(command.aggregateType(), command.aggregateId());
                final T aggregate = command.aggregateType()
                        .getDeclaredConstructor()
                        .newInstance()
                        .apply(pastEvents);
                final List<Event> newEvents = aggregate.handle(command);
                eventRepository.saveEvents(command.aggregateType(), command.aggregateId(), newEvents);
                eventPublisher.publish(newEvents);
                return CommandResult.success(command.aggregateId(), newEvents);
            } catch (Exception e) {
                return CommandResult.failure(command.aggregateId(), e);
            }
        });
    }

}
