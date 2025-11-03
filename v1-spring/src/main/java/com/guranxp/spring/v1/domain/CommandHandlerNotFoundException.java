package com.guranxp.spring.v1.domain;

public class CommandHandlerNotFoundException extends RuntimeException {
    public CommandHandlerNotFoundException(Class<?> commandType, Class<?> aggregateType) {
        super("No @CommandHandler method found for command " + commandType.getSimpleName() +
                " in aggregate " + aggregateType.getSimpleName());
    }
}