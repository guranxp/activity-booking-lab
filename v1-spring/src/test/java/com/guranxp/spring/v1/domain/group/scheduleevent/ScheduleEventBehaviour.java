package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.EventPublisher;
import com.guranxp.spring.v1.domain.group.Group;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NewClassNamingConvention")
public class ScheduleEventBehaviour {

    @Test
    public void shouldPublishEventScheduledWhenEventIsScheduledOnAnEmptyGroup() {

        // Given
        final String groupId = "groupId";
        final String eventName = "Event name";
        final EventPublisher eventPublisher = mock(EventPublisher.class);
        final List<Event> emptyEventStream = emptyList();
        final ScheduleEventCmd scheduleEventCmd = new ScheduleEventCmd(groupId, eventName);

        // When
        new Group(groupId, eventPublisher).apply(emptyEventStream).handle(scheduleEventCmd);

        // Then
        verify(eventPublisher).publish(any(EventScheduledEvent.class));
    }

    @Test
    public void shouldPublishEventSchedulingFailedEventWhenSchedulingEventWithExistingName() {

        // Given
        final String groupId = "groupId";
        final String existingEventName = "Event name";
        final EventPublisher eventPublisher = mock(EventPublisher.class);
        final List<Event> emptyEventStream = List.of(new EventScheduledEvent(groupId, existingEventName));
        final Group groupWithExistingEvent = new Group(groupId, eventPublisher).apply(emptyEventStream);
        final ScheduleEventCmd scheduleEventCmd = new ScheduleEventCmd(groupId, existingEventName);

        // When
        groupWithExistingEvent.handle(scheduleEventCmd);

        // Then
        verify(eventPublisher).publish(any(EventSchedulingFailedEvent.class));
    }

}
