package io.todo.plan.exceptions;

import static io.todo.plan.util.PlanConstants.Exceptions._NOT_FOUND;

public class PlanNotFoundException extends Exception {
    public PlanNotFoundException(String planId) {
        super("planId: " + planId + _NOT_FOUND);
    }
}
