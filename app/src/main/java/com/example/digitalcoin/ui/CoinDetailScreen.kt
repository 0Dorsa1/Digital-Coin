package com.example.digitalcoin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digitalcoin.ui.viewmodel.CoinsDetailViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.digitalcoin.model.GetCoinDetailResponse
import com.example.digitalcoin.model.GetCoinListResponse
import com.example.digitalcoin.utils.readAssetsFile
import com.google.gson.Gson

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current

    val response: GetCoinDetailResponse = Gson().fromJson(
        context.assets?.readAssetsFile("coin_detail_response_mock.json"),
        GetCoinDetailResponse::class.java
    )

    ContentSection(
        coinDetailState = CoinsDetailViewModel.ScreenState(
            isLoading = false,
            isError = false,
            response = response,
        )
    )
}

@Composable
fun CoinDetailScreen(
    id: String,
    coinsDetailViewModel: CoinsDetailViewModel = viewModel()
) {
    var onCreate by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = onCreate){
        if (onCreate){
            coinsDetailViewModel.call(id = id)
            onCreate = false
        }
    }

    ContentSection(coinDetailState = coinsDetailViewModel.state)
}

@Composable
private fun ContentSection(
    coinDetailState: CoinsDetailViewModel.ScreenState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.background(color = Color.Black)) {
            ToolBar(title = "Coin", modifier = Modifier.align(Alignment.TopCenter))
        }
        Spacer(modifier = Modifier.height(50.dp))

        Text(text = coinDetailState.response?.data?.name ?: "")

//        coinDetailState.response?.let { response ->
//
//            val painter = rememberAsyncImagePainter(
//                ImageRequest.Builder(LocalContext.current).data(data = response.data.get(0).icon)
//                    .apply(block = fun ImageRequest.Builder.() {
//                        crossfade(true)
//                    }).build()
//            )
//
//            Image(
//                painter = painter,
//                contentDescription = "Icon",
//                modifier = Modifier.size(100.dp)
//            )
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        coinDetailState.response?.let { response ->
//            Text(text = "${response.data.get(0).symbol}$", style = MaterialTheme.typography.h6)
//        }
//        Spacer(modifier = Modifier.height(10.dp))
//        coinDetailState.response?.let { response ->
//            Text(text = "${response.data.get(0).name}$", style = MaterialTheme.typography.h6)
//        }
//        Spacer(modifier = Modifier.height(70.dp))
//        Text(text = "Yesterday Price:", style = MaterialTheme.typography.h6)
//        Spacer(modifier = Modifier.height(20.dp))
//        coinDetailState.response?.let { response ->
//            Text(text = "${response.data.get(0).priceDif1Day}$", style = MaterialTheme.typography.h4)
//        }
//        Spacer(modifier = Modifier.height(50.dp))
//        Text(text = "Last Price:", style = MaterialTheme.typography.h6)
//        Spacer(modifier = Modifier.height(10.dp))
//        coinDetailState.response?.let { response ->
//            Text(text = "${response.data.get(0).lastPrice}$", style = MaterialTheme.typography.h4)
//        }
    }
}