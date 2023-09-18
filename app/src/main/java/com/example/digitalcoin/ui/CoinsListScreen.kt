package com.example.digitalcoin.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.Navigation
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digitalcoin.R
import com.example.digitalcoin.fragment.CoinsListFragmentDirections
import com.example.digitalcoin.model.DigitalCoin
import com.example.digitalcoin.model.GetCoinListResponse
import com.example.digitalcoin.theme.MyAppTheme
import com.example.digitalcoin.ui.viewmodel.CoinsListViewModel
import com.example.digitalcoin.utils.getActivity
import com.example.digitalcoin.utils.readAssetsFile
import com.google.gson.Gson

@Preview
@Composable
private fun Preview() {
    val context = LocalContext.current
    //INFO mock JSON ARRAY response
//    val typeToken: Type = object : TypeToken<ArrayList<GetCoinListResponse>?>() {}.type
//    val response: ArrayList<GetCoinListResponse> = Gson().fromJson(
//        context.assets?.readAssetsFile("mock/saving_plan_cancel_history_mock.json"),
//        typeToken
//    )

    //INFO mock JSON OBJECT response
    val response: GetCoinListResponse = Gson().fromJson(
        context.assets?.readAssetsFile("coin_list_response_mock.json"),
        GetCoinListResponse::class.java
    )

    ContentSection(
        CoinsListViewModel.ScreenState(
            isLoading = false,
            isError = false,
            response = response,
            exception = "vszjhb"

        ),
        coinListOnRetry = {},
        onItemClicked = {}
    )
}

@Composable
fun CoinsListScreen(
    coinListViewModel: CoinsListViewModel = viewModel()
) {
    val activity = LocalContext.current.getActivity()
    var onCreate by rememberSaveable { mutableStateOf(true) }

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

    MyAppTheme {
        ContentSection(
            coinListState = coinListViewModel.state,
            coinListOnRetry = {
                coinListViewModel.call(
                    pageSize = 10,
                    pageIndex = 0,
                    type = 1
                )
            },
            onItemClicked = { id ->
                Navigation.findNavController(activity as AppCompatActivity, R.id.nav_host_fragment).navigate(
                    CoinsListFragmentDirections.actionCoinsListFragmentToCoinDetailFragment(
                        id = id
                    )
                )
            }
        )
    }
}

@Composable
private fun ContentSection(
    coinListState: CoinsListViewModel.ScreenState,
    coinListOnRetry: () -> Unit,
    onItemClicked: (id: String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.background(color = Color.Black)) {
            ToolBar(title = "Coins", modifier = Modifier.align(Alignment.TopCenter))
        }

        Box(modifier = Modifier.weight(1F)) {
            if (coinListState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (coinListState.isError) {
                Text(
                    modifier = Modifier
                        .clickable {
                            coinListOnRetry()
                        }
                        .align(Alignment.Center),
                    text = "Loading failed, click here to retry"
                )

                coinListState.exception?.let { Log.d("ERROR!!!!!!", it) }
            } else {
                Column(modifier=Modifier.verticalScroll(rememberScrollState())){
                    coinListState.response?.data?.forEach { item ->
                        CoinItem(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(80.dp))
                                .background(Color.Gray)
                                .clickable {
                                    onItemClicked(item.id)
                                }
                                .padding(20.dp)
                                .fillMaxWidth(),
                            coin = item
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("PrivateResource")
@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    coin: DigitalCoin
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coin.icon)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = coin.symbol, color = Color.Black, fontSize = 16.sp)

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = "$${coin.price}", color = Color.Black, fontSize = 16.sp)
            // Display the percent change
            when {
                coin.percentChange().toDouble() > 0.0 -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp),
                            contentDescription = "Up Arrow",
                            tint = Color.Green,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = "${coin.percentChange()}%")
                    }
                }
                coin.percentChange().toDouble() < 0.0 -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp),
                            contentDescription = "Down Arrow",
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(text = "${coin.percentChange()}%")
                    }
                }
                else -> {
                    Text(text = "0.00%", color = Color.Gray)
                }
            }
        }
    }
}
