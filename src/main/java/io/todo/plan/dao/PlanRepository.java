package io.todo.plan.dao;

import io.todo.plan.dao.entity.PlanEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends MongoRepository<PlanEntity, String> {
    List<PlanEntity> findByRecurrenceId(String recurrenceId);
    List<PlanEntity> deleteByRecurrenceId(String recurrenceId);
}
