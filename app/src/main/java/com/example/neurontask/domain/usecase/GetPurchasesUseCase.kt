package com.example.neurontask.domain.usecase

import com.example.neurontask.domain.entity.Purchase
import com.example.neurontask.domain.repository.PurchaseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPurchasesUseCase @Inject constructor(private val repository: PurchaseRepository) {
    suspend operator fun invoke(): StateFlow<List<Purchase>> {
        return repository.getPurchases()
    }
}