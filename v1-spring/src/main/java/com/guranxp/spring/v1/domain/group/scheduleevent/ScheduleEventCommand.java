package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.BaseCommand;
import com.guranxp.spring.v1.domain.group.Group;

public class ScheduleEventCommand extends BaseCommand<Group> {
    public final String eventName;

    public ScheduleEventCommand(String aggregateId, Class<Group> aggregateType, String eventName) {
        super(aggregateId, aggregateType);
        this.eventName = eventName;
    }
}
