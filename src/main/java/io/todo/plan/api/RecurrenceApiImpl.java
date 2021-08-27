package io.todo.plan.api;

import io.todo.plan.exceptions.RecurrenceNotFoundException;
import io.todo.plan.model.Plan;
import io.todo.plan.model.Response;
import io.todo.plan.service.RecurrenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Service
@RequiredArgsConstructor
public class RecurrenceApiImpl implements RecurrenceApiDelegate {
    private final RecurrenceService recurrenceService;

    @Override
    public ResponseEntity<List<Plan>> getPlansByRecurrenceId(String recurrenceId) throws RecurrenceNotFoundException {
        return ResponseEntity
                .status(OK)
                .body(recurrenceService.getPlansByRecurrenceId(recurrenceId));
    }
    @Override
    public ResponseEntity<Response> updatePlansByRecurrenceId(String recurrenceId, Plan plan) throws RecurrenceNotFoundException {
        return ResponseEntity
                .status(OK)
                .body(recurrenceService.updatePlansByRecurrenceId(recurrenceId, plan));
    }
    @Override
    public ResponseEntity<Response> deletePlansByRecurrenceId(String recurrenceId) throws RecurrenceNotFoundException {
        return ResponseEntity
                .status(OK)
                .body(recurrenceService.deletePlansByRecurrenceId(recurrenceId));
    }
}