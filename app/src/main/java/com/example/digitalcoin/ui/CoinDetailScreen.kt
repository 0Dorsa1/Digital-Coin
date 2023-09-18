package com.example.digitalcoin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digitalcoin.model.GetCoinDetailResponse
import com.example.digitalcoin.ui.viewmodel.CoinsDetailViewModel
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
        if (coinDetailState.isLoading){
            Box(modifier = Modifier.weight(1F)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            Box(modifier = Modifier.background(color = Color.Black)) {
                ToolBar(title = "Coin", modifier = Modifier.align(Alignment.TopCenter))
            }
            Spacer(modifier = Modifier.height(16.dp))
            coinDetailState.response?.let { response ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current).data(data = response.data.icon)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)
                                    }).build()
                            ),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = response.data.symbol,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = response.data.name,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = response.data.price,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "1 Day", style = MaterialTheme.typography.h6)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "2 Days", style = MaterialTheme.typography.h6)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "3 Days", style = MaterialTheme.typography.h6)
                            }

                            Column {
                                Text(
                                    text = response.data.percentDifference1Day().toString(),
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = response.data.percentDifference2Days().toString(),
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = response.data.percentDifference3Days().toString(),
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
