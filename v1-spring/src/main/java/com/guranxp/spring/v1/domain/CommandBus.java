package com.guranxp.spring.v1.domain;

import java.util.concurrent.CompletableFuture;

public interface CommandBus {

    <T extends Aggregate<T>> CompletableFuture<CommandResult> dispatch(Command<T> command);

}
