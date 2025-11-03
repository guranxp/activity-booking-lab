package com.guranxp.spring.v1.domain;

import java.util.concurrent.CompletableFuture;

public class DefaultCommandGateway implements CommandGateway {
    private final CommandBus commandBus;

    public DefaultCommandGateway(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public CompletableFuture<CommandResult> send(Command command) {
        return commandBus.dispatch(command);
    }
}
