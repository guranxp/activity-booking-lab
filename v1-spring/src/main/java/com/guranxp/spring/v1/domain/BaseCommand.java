package com.guranxp.spring.v1.domain;

public abstract class BaseCommand<T extends Aggregate<T>> implements Command<T> {
    private final String aggregateId;
    private final Class<T> aggregateType;

    protected BaseCommand(String aggregateId, Class<T> aggregateType) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    @Override
    public String aggregateId() {
        return aggregateId;
    }

    @Override
    public Class<T> aggregateType() {
        return aggregateType;
    }
}
