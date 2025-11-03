package com.guranxp.spring.v1.domain.group;

import com.guranxp.spring.v1.domain.Aggregate;
import com.guranxp.spring.v1.domain.CommandHandler;
import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.EventSourcingHandler;
import com.guranxp.spring.v1.domain.group.creategroup.CreateGroupCommand;
import com.guranxp.spring.v1.domain.group.creategroup.GroupAlreadyCreatedEvent;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;
import com.guranxp.spring.v1.domain.group.scheduleevent.*;

import java.util.ArrayList;
import java.util.List;

public class Group extends Aggregate<Group> {

    private String aggregateId;
    private String groupName;
    private final List<String> scheduledEventNames = new ArrayList<>();

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void on(GroupCreatedEvent groupCreatedEvent) {
        aggregateId = groupCreatedEvent.aggregateId();
        groupName = groupCreatedEvent.groupName();
    }

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void on(EventScheduledEvent eventScheduledEvent) {
        scheduledEventNames.add(eventScheduledEvent.eventName());
    }

    @CommandHandler
    public List<Event> handle(final ScheduleEventCommand scheduleEventCmd) {
        if (scheduledEventNames.contains(scheduleEventCmd.eventName)) {
            return List.of(new EventAlreadyScheduledEvent(aggregateId, scheduleEventCmd.eventName));
        } else {
            return List.of(new EventScheduledEvent(aggregateId, scheduleEventCmd.eventName));
        }
    }

    @CommandHandler
    public List<Event> handle(final CreateGroupCommand createGroupCommand) {
        if (aggregateId != null) {
            return List.of(new GroupAlreadyCreatedEvent(aggregateId));
        } else {
            return List.of(new GroupCreatedEvent(createGroupCommand.aggregateId(), createGroupCommand.groupName()));
        }
    }
}
