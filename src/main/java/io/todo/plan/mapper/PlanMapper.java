
package io.todo.plan.mapper;

import io.todo.plan.model.Plan;
import io.todo.plan.dao.entity.PlanEntity;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlanMapper {
    PlanEntity toPlanEntity(Plan plan);
    Plan toPlan(PlanEntity planEntity);
}
