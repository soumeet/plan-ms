package io.todo.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"io.todo.plan"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PlanMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanMsApplication.class, args);
    }
}


