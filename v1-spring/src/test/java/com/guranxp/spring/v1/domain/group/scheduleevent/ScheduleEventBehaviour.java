package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.group.Group;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.defaultGroup;
import static com.guranxp.spring.v1.domain.group.GroupTestConstants.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("NewClassNamingConvention")
public class ScheduleEventBehaviour {

    @Test
    public void shouldReturnEventScheduledEventWhenScheduleEventCmdIsTriggered() {
        final List<Event> events = defaultGroup()
                .withAppliedEvents(new GroupCreatedEvent(GROUP_AGGREGATE_ID, GROUP_NAME))
                .build()
                .handle(new ScheduleEventCommand(GROUP_AGGREGATE_ID, Group.class, EVENT_NAME));
        assertThat(events, hasSize(1));
        assertThat(events, hasItem(eventScheduledEventWith(GROUP_AGGREGATE_ID, EVENT_NAME)));
    }

    @Test
    public void shouldReturnEventAlreadyScheduledEventWhenSchedulingEventWithExistingName() {
        final List<Event> events = defaultGroup()
                .withAppliedEvents(
                        new GroupCreatedEvent(GROUP_AGGREGATE_ID, GROUP_NAME),
                        new EventScheduledEvent(GROUP_AGGREGATE_ID, EVENT_NAME)
                )
                .build().handle(new ScheduleEventCommand(GROUP_AGGREGATE_ID, Group.class, EVENT_NAME));
        assertThat(events, hasSize(1));
        assertThat(events, hasItem(eventAlreadyScheduledEventWith(GROUP_AGGREGATE_ID, EVENT_NAME)));
    }

    public static Matcher<Event> eventScheduledEventWith(String expectedAggregateId, String expectedEventName) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Event event) {
                if (!(event instanceof EventScheduledEvent e)) return false;
                return expectedAggregateId.equals(e.getAggregateId()) && expectedEventName.equals(e.getEventName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("EventScheduledEvent with id=" + expectedAggregateId + " and event name=" + expectedEventName);
            }
        };
    }

    public static Matcher<Event> eventAlreadyScheduledEventWith(String expectedAggregateId, String expectedEventName) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Event event) {
                if (!(event instanceof EventAlreadyScheduledEvent e)) return false;
                return expectedAggregateId.equals(e.getAggregateId()) && expectedEventName.equals(e.getEventName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("EventAlreadyScheduledEvent with id=" + expectedAggregateId + " and event name=" + expectedEventName);
            }
        };
    }
}
