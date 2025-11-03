package com.guranxp.spring.v1.domain.group;

import com.guranxp.spring.v1.domain.Aggregate;
import com.guranxp.spring.v1.domain.Event;
import com.guranxp.spring.v1.domain.EventSourcingHandler;
import com.guranxp.spring.v1.domain.group.creategroup.CreateGroupCommand;
import com.guranxp.spring.v1.domain.group.creategroup.GroupAlreadyCreatedEvent;
import com.guranxp.spring.v1.domain.group.creategroup.GroupCreatedEvent;
import com.guranxp.spring.v1.domain.group.scheduleevent.*;

import java.util.ArrayList;
import java.util.List;

public class Group extends Aggregate {

    private String groupId;
    private String groupName;
    private final List<String> scheduledEventNames = new ArrayList<>();

    public Group apply(final List<Event> eventStream) {
        final Group result = new Group();
        eventStream.forEach(result::on);
        return result;
    }

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void on(GroupCreatedEvent groupCreatedEvent) {
        groupId = groupCreatedEvent.groupId();
        groupName = groupCreatedEvent.groupName();
    }

    @EventSourcingHandler
    @SuppressWarnings("unused")
    public void on(EventScheduledEvent eventScheduledEvent) {
        scheduledEventNames.add(eventScheduledEvent.eventName());
    }

    public List<Event> handle(final ScheduleEventCommand scheduleEventCmd) {
        if (scheduledEventNames.contains(scheduleEventCmd.eventName())) {
            return List.of(new EventAlreadyScheduledEvent(groupId, scheduleEventCmd.eventName()));
        } else {
            return List.of(new EventScheduledEvent(groupId, scheduleEventCmd.eventName()));
        }
    }

    public List<Event> handle(final CreateGroupCommand createGroupCommand) {
        if (groupId != null) {
            return List.of(new GroupAlreadyCreatedEvent(groupId));
        } else {
            return List.of(new GroupCreatedEvent(createGroupCommand.groupId(), createGroupCommand.groupName()));
        }
    }
}
