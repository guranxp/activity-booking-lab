package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.createdGroup;
import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.defaultGroup;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("NewClassNamingConvention")
public class ScheduleEventBehaviour {

    private static final String GROUP_ID = "groupId";
    private static final String GROUP_NAME = "Group name";
    private static final String EVENT_NAME = "Event name";

    @Test
    public void shouldReturnEventScheduledEventWhenScheduleEventCmdIsTriggered() {
        final List<Event> events = createdGroup(GROUP_ID, GROUP_NAME).handle(new ScheduleEventCommand(GROUP_ID, EVENT_NAME));
        assertThat(events, hasSize(1));
        assertThat(events, hasItem(new EventScheduledEvent(GROUP_ID, EVENT_NAME)));
    }

    @Test
    public void shouldReturnEventAlreadyScheduledEventWhenSchedulingEventWithExistingName() {
        final List<Event> events = defaultGroup()
                .withAppliedEvents(
                        new GroupCreatedEvent(GROUP_ID, GROUP_NAME),
                        new EventScheduledEvent(GROUP_ID, EVENT_NAME)
                )
                .build().handle(new ScheduleEventCommand(GROUP_ID, EVENT_NAME));
        assertThat(events, hasSize(1));
        assertThat(events, hasItem(new EventAlreadyScheduledEvent(GROUP_ID, EVENT_NAME)));
    }

}
