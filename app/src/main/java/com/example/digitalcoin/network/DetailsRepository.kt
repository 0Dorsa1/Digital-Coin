package com.example.digitalcoin.network

import com.example.digitalcoin.model.GetCoinListResponse
import javax.inject.Inject

class DetailsRepository @Inject constructor(val api: LivePriceApi) {
    private var response: GetCoinListResponse? = null
    suspend fun call() {
        response = api.getDetails(
            id = "61f3e31f23f1f2048a87d945"
        )
    }
}