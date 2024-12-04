package com.example.neurontask.domain.usecase

import com.example.neurontask.domain.entity.Purchase
import com.example.neurontask.domain.repository.PurchaseRepository
import javax.inject.Inject

class GetPurchasesUseCase @Inject constructor(private val repository: PurchaseRepository) {
    suspend operator fun invoke(): List<Purchase> {
        return repository.getPurchases()
    }
}