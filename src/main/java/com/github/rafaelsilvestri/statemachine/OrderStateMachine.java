package com.github.rafaelsilvestri.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class OrderStateMachine extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    @Bean
    PackAction packAction() {
        return new PackAction();
    }

    @Bean
    public StateMachineListener<OrderState, OrderEvent> listener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderState.CREATED)
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderState.CREATED)
                .target(OrderState.PACKED)
                .event(OrderEvent.PACK)
                .action(packAction())
                .guard(packAction())

                .and()

                .withExternal()
                .source(OrderState.PACKED)
                .target(OrderState.SHIPPED)
                .event(OrderEvent.SHIP)
                //.action(shipAction, sendMailToClientAction)
                //.guard(shipAction)

                .and()

                .withExternal()
                .source(OrderState.SHIPPED)
                .target(OrderState.DELIVERED)
                .event(OrderEvent.CHECK_FOR_DELIVERY)
                //.action(checkDeliveryAction)

                .and()

                .withExternal()
                .source(OrderState.DELIVERED)
                .target(OrderState.CANCELLED)
                .event(OrderEvent.CLIENT_RETURN)
                //.action(clientReturnAction)
                //.guard(clientReturnAction)

                .and()

                .withExternal()
                .source(OrderState.PACKED)
                .target(OrderState.CANCELLED)
                .event(OrderEvent.RESIGN)
                //.action(clientResignAction)
                //.guard(clientResignAction)

                .and()

                .withInternal()
                .source(OrderState.DELIVERED)
                .event(OrderEvent.SEND_SURVEY);
        //.action(sendSurveyAction)
        //.guard(sendSurveyAction)
    }
}
