package com.guranxp.spring.v1.domain;

public interface Command<T extends Aggregate<T>> {
    String aggregateId();
    Class<T> aggregateType();
}
