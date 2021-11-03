package com.github.rafaelsilvestri.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    StateMachine<OrderState, OrderEvent> stateMachine;

    @Override
    public void run(String... args) throws Exception {
        stateMachine.sendEvent(OrderEvent.PACK);
        stateMachine.sendEvent(OrderEvent.SHIP);
        stateMachine.sendEvent(OrderEvent.CHECK_FOR_DELIVERY);

    }
}
