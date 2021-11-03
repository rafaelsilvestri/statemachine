package com.github.rafaelsilvestri.statemachine;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

public class PackAction implements Action<OrderState, OrderEvent>, Guard<OrderState, OrderEvent> {

    @Override
    public void execute(StateContext<OrderState, OrderEvent> context) {
        System.out.println("Executing action for event: " + context.getEvent());
    }

    @Override
    public boolean evaluate(StateContext<OrderState, OrderEvent> context) {
        //todo: remove when the message body is sent
        if (1 == 1) { // order paid
            return true;
        }

        context.getStateMachine().setStateMachineError(
                new Exception("An order that hasn't been paid cannot be packed"));
        return false;

    }
}
