package com.guranxp.spring.v1.domain.group;

import com.guranxp.spring.v1.domain.Aggregate;
import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.EventPublisher;
import com.guranxp.spring.v1.domain.EventSourcingHandler;
import com.guranxp.spring.v1.domain.group.scheduleevent.EventScheduledEvent;
import com.guranxp.spring.v1.domain.group.scheduleevent.EventSchedulingFailedEvent;
import com.guranxp.spring.v1.domain.group.scheduleevent.ScheduleEventCmd;

import java.util.ArrayList;
import java.util.List;

public class Group extends Aggregate {

    private final String groupId;
    private final EventPublisher eventPublisher;
    private final List<String> scheduledEventNames = new ArrayList<>();

    public Group(String groupId, final EventPublisher eventPublisher) {
        this.groupId = groupId;
        this.eventPublisher = eventPublisher;
    }

    public Group apply(final List<Event> eventStream) {
        final Group result = new Group(groupId, eventPublisher);
        eventStream.forEach(result::on);
        return result;
    }

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void on(EventScheduledEvent eventScheduledEvent) {
        scheduledEventNames.add(eventScheduledEvent.eventName());
    }

    public void handle(ScheduleEventCmd scheduleEventCmd) {
        if (scheduledEventNames.contains(scheduleEventCmd.eventName())) {
            eventPublisher.publish(new EventSchedulingFailedEvent(groupId));
        } else {
            eventPublisher.publish(new EventScheduledEvent(groupId, scheduleEventCmd.eventName()));
        }
    }
}
