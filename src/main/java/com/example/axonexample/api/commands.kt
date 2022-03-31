package com.example.axonexample.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

    class CreateFoodCartCommand

    data class SelectProductCommand(
            @TargetAggregateIdentifier val foodCardId: UUID,
            val productId: UUID,
            val quantity: Int
    )

    data class DeselectProductCommand(
            @TargetAggregateIdentifier val foodCardId: UUID,
            val productId: UUID,
            val quantity: Int
    )

    data class ConfirmOrderCommand(
            @TargetAggregateIdentifier val foodCardId: UUID
    )