package com.example.axonexample.api

import java.util.*

data class FoodCartCreatedEvent(val foodCardId: UUID)

data class ProductSelectedEvent(
        val foodCardId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class ProductDeselectedEvent(
        val foodCardId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class OrderConfirmedEvent(
        val foodCardId: UUID
)