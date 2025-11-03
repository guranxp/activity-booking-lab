package com.guranxp.spring.v1.domain;

import java.util.List;

public class CommandResult {
    private final boolean success;
    private final String aggregateId;

    private CommandResult(boolean success, String aggregateId) {
        this.success = success;
        this.aggregateId = aggregateId;
    }

    public static CommandResult success(String aggregateId, List<Event> events) {
        return new CommandResult(true, aggregateId);
    }

    public static CommandResult failure(String aggregateId, Throwable error) {
        return new CommandResult(false, aggregateId);
    }

    public boolean isSuccess() {
        return success;
    }

    public String aggregateId() {
        return aggregateId;
    }

}

