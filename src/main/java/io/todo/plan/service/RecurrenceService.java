package io.todo.plan.service;

import io.todo.plan.dao.PlanRepository;
import io.todo.plan.dao.entity.PlanEntity;
import io.todo.plan.exceptions.RecurrenceNotFoundException;
import io.todo.plan.mapper.PlanMapper;
import io.todo.plan.model.Plan;
import io.todo.plan.model.Response;
import io.todo.plan.util.CommonMethods;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class RecurrenceService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public List<Plan> getPlansByRecurrenceId(String recurrenceId) throws RecurrenceNotFoundException {
        LOGGER.info("recurrenceId  : {}", recurrenceId);
        List<Plan> planList = planRepository
                .findByRecurrenceId(recurrenceId)
                .stream()
                .map(planMapper::toPlan)
                .collect(Collectors.toList());
        if (!planList.isEmpty()) {
            LOGGER.info("plans found : {}", planList.size());
        } else
            throw new RecurrenceNotFoundException(recurrenceId);
        return planList;
    }

    public Response updatePlansByRecurrenceId(String recurrenceId, Plan newPlan) throws RecurrenceNotFoundException {
        LOGGER.info("update recurrenceId  : {}", recurrenceId);
        LOGGER.info("newPlan : {}", newPlan);
        List<PlanEntity> oldPlanEntities = new ArrayList<>(planRepository
                .findByRecurrenceId(recurrenceId));

        PlanEntity newPlanEntity = planMapper.toPlanEntity(newPlan);
        oldPlanEntities = oldPlanEntities.stream()
                    .map(oldPlanEntity -> CommonMethods.update(oldPlanEntity, newPlanEntity))
                    .map(planRepository::save)
                    .collect(Collectors.toList());

        Response response = new Response();
        if(!oldPlanEntities.isEmpty()) {
            response.setCode("200");
            response.setMessage("UPDATED");
            LOGGER.info("plans updated : {}", oldPlanEntities.size());
        } else
            throw new RecurrenceNotFoundException(recurrenceId);
        return response;
    }

    public Response deletePlansByRecurrenceId(String recurrenceId) throws RecurrenceNotFoundException {
        LOGGER.info("delete recurrenceId  : {}", recurrenceId);
        List<Plan> deletedPlan = planRepository
                .deleteByRecurrenceId(recurrenceId)
                .stream()
                .map(planMapper::toPlan)
                .collect(Collectors.toList());
        Response response;
        if(!deletedPlan.isEmpty()) {
            response = new Response();
            response.setCode("200");
            response.setMessage("DELETED");
            LOGGER.info("plans deleted : {}", deletedPlan.size());
        } else
            throw new RecurrenceNotFoundException(recurrenceId);
        return response;
    }
}