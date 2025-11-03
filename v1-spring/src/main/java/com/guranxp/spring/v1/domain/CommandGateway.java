package com.guranxp.spring.v1.domain;

import java.util.concurrent.CompletableFuture;

public interface CommandGateway {

    CompletableFuture<CommandResult> send(Command command);

}
