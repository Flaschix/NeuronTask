package com.example.neurontask.data.repository

import com.example.neurontask.domain.entity.Purchase
import com.example.neurontask.domain.repository.PurchaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(

) : PurchaseRepository {

    private val _purchasesStateFlow = MutableStateFlow<List<Purchase>>(emptyList())

    private val purchasesStateFlow: StateFlow<List<Purchase>>
        get() = _purchasesStateFlow

    init {
        _purchasesStateFlow.value = listOf(
            Purchase("2022-09-10", listOf("123", "321", "1234", "4321")),
            Purchase("2022-09-08", listOf("12345", "54321")),
            Purchase("2022-09-07", listOf("123456", "65431", "1234567", "7654321"))
        )
    }

    override suspend fun getPurchases() = purchasesStateFlow
}
