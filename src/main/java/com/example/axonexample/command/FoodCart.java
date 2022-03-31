package com.example.axonexample.command;

import com.example.axonexample.api.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class FoodCart {

    @AggregateIdentifier
    private UUID foodCardId;
    private HashMap<UUID, Integer> selectedProducts;

    /*Create command handler. Handlers are used form handle the commands (create, reject, throw, etc) */
    @CommandHandler
    public FoodCart(CreateFoodCartCommand command) {

        //In this case it's food cart id
        UUID aggregateId = UUID.randomUUID();

        //Invoke the FoodCartCreatedEvent which is described in our event store
        AggregateLifecycle.apply(new FoodCartCreatedEvent(aggregateId));
    }

    @CommandHandler
    public void handle(SelectProductCommand command) {

        AggregateLifecycle.apply(new ProductSelectedEvent(
                foodCardId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand command) throws ProductDeselectingException {
        UUID productId = command.getProductId();

        if(!selectedProducts.containsKey(productId)) {
            throw new ProductDeselectingException();
        }

        AggregateLifecycle.apply(new DeselectProductCommand(
                foodCardId, productId, command.getQuantity()));
    }

    /*Event handler. In this case it's handler for FoodCartCreatedEvent
    * it called inside the command handler by AggregateLifecycle.apply
    * this event sourcing handler method will only handle the events within this aggregates lifecycle
    * */
    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        foodCardId = event.getFoodCardId();
        selectedProducts = new HashMap<>();

    }

    /**/
    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(event.getProductId(), event.getQuantity(), Integer::sum);
    }

}
