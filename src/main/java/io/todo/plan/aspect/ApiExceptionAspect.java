package io.todo.plan.aspect;

import io.todo.plan.exceptions.PlanNotCreatedException;
import io.todo.plan.exceptions.RecurrenceNotFoundException;
import io.todo.plan.model.Error;
import io.todo.plan.exceptions.PlanNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static io.todo.plan.util.PlanConstants.Exceptions.*;

@Log4j2
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiExceptionAspect {

    @ExceptionHandler(PlanNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handlePlanNotFoundError(PlanNotFoundException e) {
        var error = new Error();
        error.errorCode(PLAN_NOT_FOUND).errorMessage(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(PlanNotCreatedException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handlePlanNotCreatedError(PlanNotCreatedException e) {
        LOGGER.info("Entry into ApiExceptionAdvice#handlePlanNotCreatedError");
        Error error = new Error();
        error.errorCode(PLAN_NOT_CREATED).errorMessage(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(RecurrenceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleRecurrenceNotFoundError(RecurrenceNotFoundException e) {
        var error = new Error();
        error.errorCode(RECURRENCE_NOT_FOUND).errorMessage(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
