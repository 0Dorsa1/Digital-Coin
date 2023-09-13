package com.example.digitalcoin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCoinListResponse(
    val data: ArrayList<DigitalCoin>,
    val status: Boolean
) : Parcelable


@Parcelize
data class DigitalCoin(
    val date: Long,
    val symbol: String,
    val priceDif1Day: String,
    val price: String,
    val lastUpdate: String,
    val name: String,
    val icon: String,
    val id: String,
    val lastPrice: Double
) : Parcelable

