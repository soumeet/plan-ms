package io.todo.plan.exceptions;

public class PlanNotCreatedException extends Exception {
    public PlanNotCreatedException(String plan) {
        super( plan + "NOT CREATED");
    }
}
