package io.todo.plan.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointcutExpressions {

    /**
     * This method is like a variable for Aspect's Pointcut Expression.
     * The below expression is supposed to be executed for all methods in the mentioned package
     */
    @Pointcut("execution(* io.todo.plan.service.*.*(..))")
    public void pointcut() {
    }
}
