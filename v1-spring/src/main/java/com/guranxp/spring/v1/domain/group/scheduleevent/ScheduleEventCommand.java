package com.guranxp.spring.v1.domain.group.scheduleevent;

import com.guranxp.spring.v1.domain.Command;

public record ScheduleEventCommand(String groupId, String eventName) implements Command {
}
