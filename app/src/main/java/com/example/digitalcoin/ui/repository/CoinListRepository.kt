package com.example.digitalcoin.ui.repository

import com.example.digitalcoin.model.GetCoinListResponse
import com.example.digitalcoin.network.LivePriceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinListRepository @Inject constructor(private val api: LivePriceApi) {

    fun call(pageSize: Int, pageIndex: Int, type: Int): Flow<GetCoinListResponse> {
       return DataSource(api = api, pageSize = pageSize, pageIndex = pageIndex, type = type).result
           .catch { exception ->
               throw exception
           }
    }

    private class DataSource(private val api: LivePriceApi, pageSize: Int, pageIndex: Int, type: Int){
        val result: Flow<GetCoinListResponse> = flow {
            val response = api.getCoins(
                pageSize = pageSize,
                pageIndex = pageIndex,
                type = type
            )
            emit(response)
        }

//        val result: Flow<GetCoinListResponse> = flow {
//            delay(2000)
//            val response = GetCoinListResponse(
//                data = arrayListOf(),
//                status = true
//            )
//
//            emit(response)
//        }
    }
}