package com.example.neurontask.data.mapper

import com.example.neurontask.data.entity.PurchaseDto
import com.example.neurontask.domain.entity.Purchase
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PurchaseMapper @Inject constructor(

) {

    fun mapPurchaseDtoListToPurchaseList(purchaseDtoList: List<PurchaseDto>): List<Purchase>
    = purchaseDtoList.map { mapPurchaseDtoToPurchase(it) }


    private fun mapPurchaseDtoToPurchase(purchaseDto: PurchaseDto): Purchase{
        return Purchase(
            date = fullDateToDefaultDate(purchaseDto.date),
            names = purchaseDto.name
        )
    }

    private fun fullDateToDefaultDate(str: String): String{
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val dateTime = LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC))
        return dateTime.format(formatter)
    }
}