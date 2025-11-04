package com.guranxp.spring.v1.infrastructure.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventJpaRepositoryTest {

    @Autowired
    private EventJpaRepository repository;

    @Test
    void shouldSaveAndLoadEventsByAggregateId() {
        // Create and save an event
        EventEntity event = new EventEntity();
        event.setEventId(UUID.randomUUID().toString());
        event.setAggregateType("com.guranxp.spring.v1.domain.Group");
        event.setAggregateId("group-123");
        event.setEventType("GroupCreatedEvent");
        event.setPayload("{\"name\":\"Test Group\"}");
        event.setTimestamp(Instant.now());
        event.setVersion(1);

        repository.save(event);

        // Load events by aggregate type and ID
        List<EventEntity> events = repository.findByAggregateTypeAndAggregateIdOrderByVersionAsc(
                "com.guranxp.spring.v1.domain.Group", "group-123");

        // Verify
        assertThat(events).hasSize(1);
        EventEntity loaded = events.get(0);
        assertThat(loaded.getAggregateId()).isEqualTo("group-123");
        assertThat(loaded.getPayload()).contains("Test Group");
        assertThat(loaded.getVersion()).isEqualTo(1);
    }
}
