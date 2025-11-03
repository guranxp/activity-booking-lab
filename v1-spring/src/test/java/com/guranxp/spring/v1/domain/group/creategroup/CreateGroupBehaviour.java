package com.guranxp.spring.v1.domain.group.creategroup;

import com.guranxp.spring.v1.domain.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.guranxp.spring.v1.domain.group.GroupTestBuilder.defaultGroup;
import static com.guranxp.spring.v1.domain.group.GroupTestConstants.GROUP_ID;
import static com.guranxp.spring.v1.domain.group.GroupTestConstants.GROUP_NAME;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SuppressWarnings("NewClassNamingConvention")
public class CreateGroupBehaviour {

    @Test
    public void shouldReturnGroupCreatedEventWhenCreateGroupCommandIsTriggered() {
        final List<Event> resultingEvents = defaultGroup()
                .build()
                .handle(new CreateGroupCommand(GROUP_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(new GroupCreatedEvent(GROUP_ID, GROUP_NAME)));
    }

    @Test
    public void shouldReturnGroupAlreadyCreatedEventWhenCreateGroupCommandIsTriggeredOnCreatedGroup() {
        final List<Event> resultingEvents = defaultGroup()
                .withAppliedEvents(new GroupCreatedEvent(GROUP_ID, GROUP_NAME))
                .build()
                .handle(new CreateGroupCommand(GROUP_ID, GROUP_NAME));
        assertThat(resultingEvents, hasSize(1));
        assertThat(resultingEvents, hasItem(new GroupAlreadyCreatedEvent(GROUP_ID)));
    }

}
