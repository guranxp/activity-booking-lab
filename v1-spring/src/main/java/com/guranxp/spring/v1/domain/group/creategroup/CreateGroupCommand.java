package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.Command;

public record CreateGroupCommand(String groupId, String groupName) implements Command {
}
