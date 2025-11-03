package com.guranxp.spring.v1.domain;

import com.guranxp.spring.v1.domain.group.Group;
import com.guranxp.spring.v1.domain.group.creategroup.CreateGroupCommand;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("NewClassNamingConvention")
class DefaultCommandBusBehaviour {

    @Test
    public void shouldNotFail() throws ExecutionException, InterruptedException {

        // Given
        final String groupId = UUID.randomUUID().toString();
        final String groupName = "My Test Group";

        final EventRepository eventRepository = mock(EventRepository.class);
        given(eventRepository.loadEvents(Group.class, groupId)).willReturn(emptyList());

        final EventPublisher eventPublisher = mock(EventPublisher.class);
        final List<Event> expectedEvents = List.of(new GroupCreatedEvent(groupId, groupName));

        // When
        final CommandResult commandResult = new DefaultCommandBus(eventRepository, eventPublisher)
                .dispatch(new CreateGroupCommand(groupId, groupName))
                .get();

        // Then
        assertTrue(commandResult.isSuccess());
        verify(eventRepository).saveEvents(Group.class, groupId, expectedEvents);
        verify(eventPublisher).publish(expectedEvents);
    }

}