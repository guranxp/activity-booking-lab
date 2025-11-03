package com.guranxp.spring.v1.domain.group;

import com.guranxp.spring.v1.domain.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class GroupTestBuilder {

    private final List<Event> eventsToApply = new ArrayList<>();

    public static GroupTestBuilder defaultGroup() {
        return new GroupTestBuilder();
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

    public Group build() {
        return new Group().apply(eventsToApply);
    }

}
