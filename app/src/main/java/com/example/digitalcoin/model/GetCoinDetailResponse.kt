package com.example.digitalcoin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCoinDetailResponse(
    val data: DetailedCoin,
    val status: Boolean
) : Parcelable

@Parcelize
data class DetailedCoin(
    val date: Long,
    val symbol: String,
    val priceDif2Day: String,
    val priceDif1Day: String,
    val priceDif3Day: String,
    val priceDif4Day: String,
    val icon: String,
    val type: Int,
    val priceDif7Day: String,
    val price: String,
    val priceDif6Day: String,
    val priceDif5Day: String,
    val lastUpdate: String,
    val name: String,
    val links: List<Link>,
    val id: String,
    val order: Int,
    val lastPrice: Double
) : Parcelable{
    fun percentDifference1Day(): String {
        val priceDif1DayDouble = priceDif1Day.replace(",", "").toDouble()
        if (priceDif1DayDouble != 0.0) {
            val percentage = ((lastPrice - priceDif1DayDouble) / priceDif1DayDouble) * 100
            return String.format("%.2f", percentage)
        }
        return "0.00"
    }

    fun percentDifference2Days(): String {
        val priceDif2DayDouble = priceDif2Day.replace(",", "").toDouble()
        if (priceDif2DayDouble != 0.0) {
            val percentage = ((lastPrice - priceDif2DayDouble) / priceDif2DayDouble) * 100
            return String.format("%.2f", percentage)
        }
        return "0.00"
    }

    fun percentDifference3Days(): String {
        val priceDif3DayDouble = priceDif3Day.replace(",", "").toDouble()
        if (priceDif3DayDouble != 0.0) {
            val percentage = ((lastPrice - priceDif3DayDouble) / priceDif3DayDouble) * 100
            return String.format("%.2f", percentage)
        }
        return "0.00"
    }



}

@Parcelize
data class Link(
    val image: String,
    val name: String,
    val link: String
) : Parcelable