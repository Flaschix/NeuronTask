package com.example.neurontask.domain.repository

import com.example.neurontask.domain.entity.Purchase
import kotlinx.coroutines.flow.StateFlow

interface PurchaseRepository {
    suspend fun getPurchases(): StateFlow<List<Purchase>>
}