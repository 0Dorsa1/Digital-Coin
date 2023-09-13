package com.example.digitalcoin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
) : Parcelable


@Parcelize
data class Link(
    val image: String,
    val name: String,
    val link: String
) : Parcelable