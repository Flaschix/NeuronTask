package com.example.neurontask.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDataDto(
    val data: List<PurchaseDto>
)