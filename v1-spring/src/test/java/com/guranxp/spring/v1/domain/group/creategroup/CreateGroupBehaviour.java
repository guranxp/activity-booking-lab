package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.createdGroup;
import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.emptyGroup;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("NewClassNamingConvention")
public class CreateGroupBehaviour {

    public static final String GROUP_ID = "groupId";
    public static final String GROUP_NAME = "Group name";

    @Test
    public void shouldReturnGroupCreatedEventWhenCreateGroupCommandIsTriggered() {
        final List<Event> resultingEvents = emptyGroup().handle(new CreateGroupCommand(GROUP_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(new GroupCreatedEvent(GROUP_ID, GROUP_NAME)));
    }

    @Test
    public void shouldReturnGroupAlreadyCreatedEventWhenCreateGroupCommandIsTriggeredOnCreatedGroup() {
        final List<Event> resultingEvents = createdGroup(GROUP_ID, GROUP_NAME).handle(new CreateGroupCommand(GROUP_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(new GroupAlreadyCreatedEvent(GROUP_ID)));
    }

}
