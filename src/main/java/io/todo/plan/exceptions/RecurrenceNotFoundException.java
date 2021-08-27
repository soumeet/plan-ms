package io.todo.plan.exceptions;

import static io.todo.plan.util.PlanConstants.Exceptions._NOT_FOUND;

public class RecurrenceNotFoundException extends Exception {
    public RecurrenceNotFoundException(String recurrenceId) {
        super("recurrenceId: " + recurrenceId + _NOT_FOUND);
    }
}
