package com.example.neurontask.presentation.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neurontask.domain.entity.Purchase
import com.example.neurontask.domain.usecase.GetPurchasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasesScreenViewModel @Inject constructor(
    private val getPurchasesUseCase: GetPurchasesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PurchaseScreenState>(PurchaseScreenState.Init)
    val state: StateFlow<PurchaseScreenState> = _state.asStateFlow()

    init {
        loadPurchases()
    }

    private fun loadPurchases() {
        viewModelScope.launch {
            _state.value = PurchaseScreenState.Loading
            try {
                val purchases = getPurchasesUseCase().first()
                _state.value = PurchaseScreenState.Success(groupPurchasesByDate(purchases))
            } catch (e: Exception) {
                _state.value = PurchaseScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun groupPurchasesByDate(purchases: List<Purchase>): Map<String, List<String>> {
        return purchases.groupBy { it.date }
            .mapValues { (_, items) -> items.flatMap { it.names } }
    }
}

