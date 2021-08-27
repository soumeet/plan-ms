package io.todo.plan.api;

import io.todo.plan.exceptions.PlanNotCreatedException;
import io.todo.plan.model.Frequency;
import io.todo.plan.model.Plan;
import io.todo.plan.exceptions.PlanNotFoundException;
import io.todo.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlanApiImpl implements PlanApiDelegate {
    private final PlanService planService;

    @Override
    public ResponseEntity<Plan> createPlan(Plan plan) throws PlanNotCreatedException {
        return ResponseEntity
            .status(CREATED)
            .body(planService.createPlan(plan));
    }

    @Override
    public ResponseEntity<Plan> deletePlan(String planId) throws PlanNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(planService.deletePlan(planId));
    }

    @Override
    public ResponseEntity<Plan> getPlanById(String planId) throws PlanNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(planService.getPlanById(planId));
    }

    @Override
    public ResponseEntity<List<Plan>> getPlansWithFilter(String recurrenceId, OffsetDateTime startTime,
        OffsetDateTime endTime) {
        return ResponseEntity
            .status(OK)
            .body(planService.getPlansWithFilter(recurrenceId, startTime, endTime));
    }

    @Override
    public ResponseEntity<Plan> updatePlan(String planId, Plan plan) throws PlanNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(planService.updatePlan(planId, plan));
    }
}