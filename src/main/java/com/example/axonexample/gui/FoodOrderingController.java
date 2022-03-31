package com.example.axonexample.gui;

import com.example.axonexample.api.CreateFoodCartCommand;
import com.example.axonexample.api.FindFoodCartQuery;
import com.example.axonexample.query.FoodCartView;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class FoodOrderingController {

    /*The gateway between controller and command store and provides an API to send commands
    * Can do 'send', 'sendAndWait', 'sendAndWait with timeout'
    * */
    private final CommandGateway commandGateway;

    /*The gateway between controller and queries store and provides an API to handle queries*/
    private final QueryGateway queryGateway;

    public FoodOrderingController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping("/create")
    public void handle(){

        commandGateway.send(new CreateFoodCartCommand());
    }

    @GetMapping("/foodcart/{foodCartId}")
    public CompletableFuture<FoodCartView> handle(@PathVariable("foodCartId") String foodCartId){

        return queryGateway.query(new FindFoodCartQuery(UUID.fromString(foodCartId)), ResponseTypes.instanceOf(FoodCartView.class));

    }

}
