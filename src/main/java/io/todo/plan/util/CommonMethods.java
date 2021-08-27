package io.todo.plan.util;

import io.todo.plan.dao.entity.PlanEntity;
import io.todo.plan.model.Response;

import java.time.OffsetDateTime;

public class CommonMethods {
    public static boolean isNotNullOrEmpty(Object o) {
        return o != null;
    }
    public static boolean isNotNullOrEmpty(String s) {
        return s != null && !"".equals(s);
    }

    public static boolean filterUsing(String value, String filter) {
        if(isNotNullOrEmpty(filter)) {
            if (isNotNullOrEmpty(value))
                return value.contains(filter);
            return false;
        }
        return true;
    }

    public static boolean filterUsing(String value, OffsetDateTime filter, String operator) {
        if(isNotNullOrEmpty(filter)) {
            if (isNotNullOrEmpty(value))
                if("<".equals(operator))
                    return OffsetDateTime.parse(value).compareTo(filter) > 0;
                else if(">".equals(operator))
                    return OffsetDateTime.parse(value).compareTo(filter) < 0;
                else if("=".equals(operator))
                    return OffsetDateTime.parse(value).compareTo(filter) == 0;
            return false;
        }
        return true;
    }

    public static PlanEntity update(PlanEntity oldPlanEntity, PlanEntity newPlanEntity) {
        oldPlanEntity.setStartTime(newPlanEntity.getStartTime());
        oldPlanEntity.setEndTime(newPlanEntity.getEndTime());
        oldPlanEntity.setName(newPlanEntity.getName());
        return oldPlanEntity;
    }

}
