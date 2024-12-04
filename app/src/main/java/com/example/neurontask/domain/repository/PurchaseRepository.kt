package com.example.neurontask.domain.repository

import com.example.neurontask.domain.entity.Purchase

interface PurchaseRepository {
    suspend fun getPurchases(): List<Purchase>
}