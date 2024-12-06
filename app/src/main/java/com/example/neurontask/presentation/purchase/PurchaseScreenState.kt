package com.example.neurontask.presentation.purchase

sealed class PurchaseScreenState {

    data object Init: PurchaseScreenState()

    data object Loading: PurchaseScreenState()

    data class Success(val data: Map<String, List<String>>): PurchaseScreenState()

    data class Error(val msg: String): PurchaseScreenState()
}