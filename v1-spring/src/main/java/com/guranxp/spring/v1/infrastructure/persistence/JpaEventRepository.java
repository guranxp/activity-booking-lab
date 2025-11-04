package com.guranxp.spring.v1.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guranxp.spring.v1.domain.Aggregate;
import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.EventRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaEventRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final ObjectMapper objectMapper;

    public JpaEventRepository(EventJpaRepository eventJpaRepository, ObjectMapper objectMapper) {
        this.eventJpaRepository = eventJpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Event> loadEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId) {
        final String aggregateTypeName = aggregateType.getName();
        return eventJpaRepository
                .findByAggregateTypeAndAggregateIdOrderByVersionAsc(aggregateTypeName, aggregateId)
                .stream()
                .map(entity -> deserializeEvent(entity.getPayload(), entity.getEventType()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveEvents(Class<? extends Aggregate<?>> aggregateType, String aggregateId, List<Event> events) {
        final String aggregateTypeName = aggregateType.getName();
        int currentVersion = getCurrentVersion(aggregateTypeName, aggregateId);

        final List<EventEntity> entities = new ArrayList<>();
        for (final Event event : events) {
            final EventEntity entity = new EventEntity();
            entity.setEventId(event.id());
            entity.setAggregateType(aggregateTypeName);
            entity.setAggregateId(aggregateId);
            entity.setEventType(event.getClass().getName());
            entity.setPayload(serializeEvent(event));
            entity.setTimestamp(Instant.now());
            entity.setVersion(++currentVersion);
            entities.add(entity);
        }

        eventJpaRepository.saveAll(entities);
    }

    private int getCurrentVersion(String aggregateType, String aggregateId) {
        return eventJpaRepository
                .findByAggregateTypeAndAggregateIdOrderByVersionAsc(aggregateType, aggregateId)
                .stream()
                .mapToInt(EventEntity::getVersion)
                .max()
                .orElse(0);
    }

    private String serializeEvent(Event event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialization failed", e);
        }
    }

    private Event deserializeEvent(String payload, String eventType) {
        try {
            Class<?> clazz = Class.forName(eventType);
            return (Event) objectMapper.readValue(payload, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization failed", e);
        }
    }

}
