package io.todo.plan.dao.entity;

import io.todo.plan.model.Frequency;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "plan")
public class PlanEntity {
    @Id
    private String id;
    private String recurrenceId;
    private String name;
    private Frequency frequency;
    private String startTime;
    private String endTime;
}
