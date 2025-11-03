package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.BaseCommand;
import com.guranxp.spring.v1.domain.group.Group;

public class CreateGroupCommand extends BaseCommand<Group> {
    private final String groupName;

    public CreateGroupCommand(String aggregateId, String groupName) {
        super(aggregateId, Group.class);
        this.groupName = groupName;
    }

    public String groupName() {
        return groupName;
    }
}

