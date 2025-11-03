package com.guranxp.spring.v1.domain;

import java.util.concurrent.CompletableFuture;

public class DefaultCommandBus implements CommandBus {
    @Override
    public CompletableFuture<CommandResult> dispatch(Command command) {
        return null;
    }
}
