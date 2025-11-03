package com.guranxp.spring.v1.domain;

import java.util.concurrent.CompletableFuture;

public interface CommandBus {

    CompletableFuture<CommandResult> dispatch(Command command);

}
