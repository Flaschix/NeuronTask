package com.example.neurontask.data.repository

import com.example.neurontask.data.entity.PurchaseDataDto
import com.example.neurontask.data.entity.PurchaseDto
import com.example.neurontask.data.mapper.PurchaseMapper
import com.example.neurontask.domain.entity.Purchase
import com.example.neurontask.domain.repository.PurchaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseMapper: PurchaseMapper
) : PurchaseRepository {

    private val _purchasesStateFlow = MutableStateFlow<List<Purchase>>(emptyList())

    private val purchasesStateFlow: StateFlow<List<Purchase>>
        get() = _purchasesStateFlow.asStateFlow()

    init {
        loadStaticData()
    }

    private fun loadStaticData() {
        val jsonData = """
            {
               "data":[
                  {
                     "date":"2022-09-10T21:55:33Z",
                     "name":[
                        "123",
                        "321"
                     ]
                  },
                  {
                     "date":"2022-09-10T21:50:33Z",
                     "name":[
                        "1234",
                        "4321"
                     ]
                  },
                  {
                     "date":"2022-09-08T01:55:33Z",
                     "name":[
                        "12345",
                        "54321"
                     ]
                  },
                  {
                     "date":"2022-09-07T21:55:33Z",
                     "name":[
                        "123456",
                        "654321"
                     ]
                  },
                  {
                     "date":"2022-09-07T11:55:33Z",
                     "name":[
                        "1234567",
                        "7654321"
                     ]
                  }
               ]
            }
        """
        val purchaseData = Json.decodeFromString<PurchaseDataDto>(jsonData)
        _purchasesStateFlow.value = purchaseMapper.mapPurchaseDtoListToPurchaseList(purchaseData.data)
    }


    override suspend fun getPurchases() = purchasesStateFlow
}
