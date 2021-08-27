package io.todo.plan.service;

import io.todo.plan.dao.PlanRepository;
import io.todo.plan.dao.entity.PlanEntity;
import io.todo.plan.exceptions.PlanNotCreatedException;
import io.todo.plan.exceptions.PlanNotFoundException;
import io.todo.plan.mapper.PlanMapper;
import io.todo.plan.model.Frequency;
import io.todo.plan.model.Plan;
import io.todo.plan.util.CommonMethods;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.todo.plan.util.CommonMethods.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public Plan deletePlan(String planId) throws PlanNotFoundException {
        LOGGER.info("received planId : {}", planId);
        var deletedPlan = planRepository
                .findById(planId)
                .orElseThrow(()-> new PlanNotFoundException(planId));

        planRepository.deleteById(planId);

        return planMapper.toPlan(deletedPlan);
    }

    public Plan getPlanById(String planId) throws PlanNotFoundException {
        LOGGER.info("received planId : {}", planId);
        Plan plan = null;
        PlanEntity planEntity = planRepository
                .findById(planId)
                .orElseThrow(()-> new PlanNotFoundException(planId));
        plan = planMapper.toPlan(planEntity);
        return plan;
    }

    public Plan updatePlan(String planId, Plan plan) throws PlanNotFoundException {
        LOGGER.info("received plan : {}", plan);
        var fetchedPlan = planRepository
                .findById(planId)
                .orElseThrow(()-> new PlanNotFoundException(planId));
        var updatedPlan = planMapper.toPlanEntity(plan);

        updatedPlan = CommonMethods.update(fetchedPlan, updatedPlan);
        updatedPlan = planRepository.save(updatedPlan);
        LOGGER.info("updated plan: {}", updatedPlan);

        return planMapper.toPlan(updatedPlan);
    }


    public Plan createPlan(Plan plan) throws PlanNotCreatedException {
        LOGGER.info("received plan : {}", plan);
        List<PlanEntity> generatedPlanEntityList = null;
        if(Frequency.ONCE.getValue().equals(plan.getFrequency().getValue())) {
            generatedPlanEntityList = copyPlans(plan, 1);
        } else if(Frequency.DAILY.getValue().equals(plan.getFrequency().getValue())) {
            generatedPlanEntityList = copyPlans(plan, 7);
        } else if(Frequency.MONTHLY.getValue().equals(plan.getFrequency().getValue())) {
            generatedPlanEntityList = copyPlans(plan, 12);
        }
        PlanEntity createdPlan;
        try {
            List<PlanEntity> plans = planRepository.insert(generatedPlanEntityList);
            createdPlan = plans.get(0);
        } catch (Exception e) {
            throw new PlanNotCreatedException(plan.toString());
        }
        return planMapper.toPlan(createdPlan);
    }

    public List<PlanEntity> copyPlans(Plan plan, int noOfPlan) {
        String recurrenceId = UUID.randomUUID().toString();
        OffsetDateTime planStartTime = OffsetDateTime.parse(plan.getStartTime());
        OffsetDateTime planEndTime = OffsetDateTime.parse(plan.getEndTime());
        List<PlanEntity> planEntityList = new ArrayList();
        PlanEntity newPlan;
        for(int i = 0; i < noOfPlan; i++) {
            String planId = UUID.randomUUID().toString();

            newPlan = PlanEntity.builder()
                .id(planId)
                .name(plan.getName())
                .recurrenceId(recurrenceId)
                .frequency(plan.getFrequency())
                .startTime(planStartTime.plusDays(i).toString())
                .endTime(planEndTime.plusDays(i).toString())
                .build();
            LOGGER.info("generated plan : {}", newPlan);
            planEntityList.add(newPlan);

            planStartTime = planStartTime.plusDays(1);
            planEndTime = planEndTime.plusDays(1);
        }
        return planEntityList;
    }


    public List<Plan> getPlansWithFilter(String recurrenceId, OffsetDateTime startTime, OffsetDateTime endTime) {
        List<Plan> plans;

        plans = planRepository
            .findAll()
            .stream()
            .filter(planEntity -> filterUsing(planEntity.getRecurrenceId(), recurrenceId))
            .filter(planEntity -> filterUsing(planEntity.getStartTime(), startTime, "<"))
            .filter(planEntity -> filterUsing(planEntity.getEndTime(), endTime, ">"))
            .map(planEntity -> planMapper.toPlan(planEntity))
            .collect(Collectors.toList());

        return plans;
    }
}