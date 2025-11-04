package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.Event;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.defaultGroup;
import static com.guranxp.spring.v1.domain.group.GroupTestConstants.GROUP_AGGREGATE_ID;
import static com.guranxp.spring.v1.domain.group.GroupTestConstants.GROUP_NAME;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("NewClassNamingConvention")
public class CreateGroupBehaviour {

    @Test
    public void shouldReturnGroupCreatedEventWhenCreateGroupCommandIsTriggered() {
        final List<Event> resultingEvents = defaultGroup()
                .build()
                .handle(new CreateGroupCommand(GROUP_AGGREGATE_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(groupCreatedEventWith(GROUP_AGGREGATE_ID, GROUP_NAME)));
    }

    @Test
    public void shouldReturnGroupAlreadyCreatedEventWhenCreateGroupCommandIsTriggeredOnCreatedGroup() {
        final List<Event> resultingEvents = defaultGroup()
                .withAppliedEvents(new GroupCreatedEvent(GROUP_AGGREGATE_ID, GROUP_NAME))
                .build()
                .handle(new CreateGroupCommand(GROUP_AGGREGATE_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(groupAlreadyCreatedEventWith(GROUP_AGGREGATE_ID)));
    }

    public static Matcher<Event> groupCreatedEventWith(String expectedAggregateId, String expectedGroupName) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Event event) {
                if (!(event instanceof GroupCreatedEvent e)) return false;
                return expectedAggregateId.equals(e.getAggregateId()) && expectedGroupName.equals(e.getGroupName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("GroupCreatedEvent with id=" + expectedAggregateId + " and group name=" + expectedGroupName);
            }
        };
    }

    public static Matcher<Event> groupAlreadyCreatedEventWith(String expectedAggregateId) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Event event) {
                if (!(event instanceof GroupAlreadyCreatedEvent e)) return false;
                return expectedAggregateId.equals(e.getAggregateId());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("GroupAlreadyCreatedEvent with id=" + expectedAggregateId);
            }
        };
    }

}
