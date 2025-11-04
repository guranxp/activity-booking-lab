package com.guranxp.spring.v1.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByAggregateTypeAndAggregateIdOrderByVersionAsc(String aggregateType, String aggregateId);
}
