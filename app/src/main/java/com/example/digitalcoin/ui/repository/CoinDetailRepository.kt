package com.example.digitalcoin.ui.repository

import com.example.digitalcoin.model.GetCoinDetailResponse
import com.example.digitalcoin.network.LivePriceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(private val api: LivePriceApi) {
    fun call(id: String): Flow<GetCoinDetailResponse> {
        return DataSource(api = api, id = id).result
            .catch { exception ->
                throw exception
            }
    }

    private class DataSource(private val api: LivePriceApi, id: String) {
        val result: Flow<GetCoinDetailResponse> = flow {
            val response = api.getDetails(
                id = id
            )
            emit(response)
        }
    }
}