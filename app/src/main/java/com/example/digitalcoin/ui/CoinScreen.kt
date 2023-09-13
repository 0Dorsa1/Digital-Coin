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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digitalcoin.ui.viewmodel.CoinsListViewModel

//@Preview
//@Composable
//private fun Preview() {
//    CoinUI()
//}

@Composable
fun CoinUI(state: CoinsListViewModel.ScreenState) {
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
        state.response?.let { response ->

            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = response.data.get(0).icon)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
            )

            Image(
                painter = painter,
                contentDescription = "Icon",
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        state.response?.let { response ->
            Text(text = "${response.data.get(0).symbol}$", style = MaterialTheme.typography.h6)
        }
        Spacer(modifier = Modifier.height(10.dp))
        state.response?.let { response ->
            Text(text = "${response.data.get(0).name}$", style = MaterialTheme.typography.h6)
        }
        Spacer(modifier = Modifier.height(70.dp))
        Text(text = "Yesterday Price:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(20.dp))
        state.response?.let { response ->
            Text(text = "${response.data.get(0).priceDif1Day}$", style = MaterialTheme.typography.h4)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Last Price:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        state.response?.let { response ->
            Text(text = "${response.data.get(0).lastPrice}$", style = MaterialTheme.typography.h4)
        }


    }
}