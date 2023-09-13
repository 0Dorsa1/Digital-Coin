package com.example.digitalcoin.ui

import android.util.Log
import androidx.appcompat.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalcoin.model.DigitalCoin
import com.example.digitalcoin.model.GetCoinListResponse
import com.example.digitalcoin.ui.viewmodel.CoinsListViewModel
import com.example.digitalcoin.utils.getActivity

@Preview
@Composable
private fun Preview() {

    ContentSection(
        CoinsListViewModel.ScreenState(
            isLoading = false,
            isError = false,
            response = GetCoinListResponse(
                data = arrayListOf(
                    DigitalCoin(
                        date = 10000,
                        symbol = "ABC",
                        priceDif1Day = "10%",
                        price = "26000",
                        lastUpdate = "",
                        name = "Bitcoin",
                        icon = "",
                        id = "123",
                        lastPrice = 10000.0
                    ),
                    DigitalCoin(
                        date = 10000,
                        symbol = "DEF",
                        priceDif1Day = "10%",
                        price = "26000",
                        lastUpdate = "",
                        name = "Bitcoin",
                        icon = "",
                        id = "123",
                        lastPrice = 10000.0
                    )
                ),
                status = true,
            ),
            exception = "vszjhb"

        ),
        coinListOnRetry = {}
    )
}

@Composable
fun CoinsListScreen(
    coinListViewModel: CoinsListViewModel = viewModel()
) {
    var onCreate by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = onCreate) {
        if (onCreate) {
            coinListViewModel.call(
                pageSize = 10,
                pageIndex = 0,
                type = 1
            )
            onCreate = false
        }
    }

    ContentSection(
        coinListState = coinListViewModel.state,
        coinListOnRetry = {
            coinListViewModel.call(
                pageSize = 10,
                pageIndex = 0,
                type = 1
            )
        }
    )
}

@Composable
private fun ContentSection(
    coinListState: CoinsListViewModel.ScreenState,
    coinListOnRetry: () -> Unit
) {
    val activity = LocalContext.current.getActivity()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.background(color = Color.Black)) {
            ToolBar(title = "Coins", modifier = Modifier.align(Alignment.TopCenter))
        }
        Box {
            if (coinListState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (coinListState.isError) {
                Text(
                    modifier = Modifier.clickable {
                        coinListOnRetry()
                    },
                    text = "Loading failed, click here to retry"
                )

                coinListState.exception?.let { Log.d("ERROR!!!!!!", it) }
            } else {
                Column {
                    coinListState.response?.data?.forEach { item ->
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(80.dp))
                                .background(Color.Gray)
                                .padding(20.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item.symbol, color = Color.White)
                                Spacer(modifier = Modifier.width(270.dp))

                                Icon(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {  },
                                    painter = painterResource(id = R.drawable.abc_ic_arrow_drop_right_black_24dp),
                                    contentDescription = "arrow",
                                    tint = Color.White
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CoinItem(coin: DigitalCoin) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

    }
}
