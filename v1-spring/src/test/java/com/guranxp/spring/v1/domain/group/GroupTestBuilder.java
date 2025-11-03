package com.guranxp.spring.v1.domain.group;

import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class GroupTestBuilder {

    private final List<Event> eventsToApply = new ArrayList<>();

    public static Group emptyGroup() {
        return defaultGroup().build();
    }

    public static Group createdGroup(String groupId, String groupName) {
        return defaultGroup().withAppliedEvents(new GroupCreatedEvent(groupId, groupName)).build();
    }

    public static GroupTestBuilder defaultGroup() {
        return new GroupTestBuilder();
    }

    public Group build() {
        return new Group().apply(eventsToApply);
    }

    public GroupTestBuilder withAppliedEvent(final Event eventToApply) {
        return withAppliedEvents(singletonList(eventToApply));
    }

    public GroupTestBuilder withAppliedEvents(final Event... eventsToApply) {
        return withAppliedEvents(Arrays.asList(eventsToApply));
    }

    public GroupTestBuilder withAppliedEvents(final List<Event> eventsToApply) {
        this.eventsToApply.addAll(eventsToApply);
        return this;
    }

}
