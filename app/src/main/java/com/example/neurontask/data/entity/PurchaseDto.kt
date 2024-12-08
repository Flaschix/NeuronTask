package com.example.neurontask.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDto(
    val date: String,
    val name: List<String>
)