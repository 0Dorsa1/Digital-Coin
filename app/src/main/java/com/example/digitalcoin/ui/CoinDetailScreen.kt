package com.example.digitalcoin.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.digitalcoin.model.GetCoinDetailResponse
import com.example.digitalcoin.theme.cardBackground
import com.example.digitalcoin.theme.primaryBackground
import com.example.digitalcoin.theme.primaryText
import com.example.digitalcoin.theme.secondaryText
import com.example.digitalcoin.ui.viewmodel.CoinsDetailViewModel
import com.example.digitalcoin.utils.readAssetsFile
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    LaunchedEffect(key1 = onCreate) {
        if (onCreate) {
            coinsDetailViewModel.call(id = id)
            onCreate = false
        }
    }

    ContentSection(coinDetailState = coinsDetailViewModel.state)
}

@Composable
private fun ContentSection(
    coinDetailState: CoinsDetailViewModel.ScreenState
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val data: Intent? = result.data
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        coinDetailState.response?.let { response ->
            ToolBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Coins"
            )

            if (coinDetailState.isLoading) {
                Box(modifier = Modifier.weight(1F)) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = MaterialTheme.colors.cardBackground)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )

                {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = response.data.icon)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                }).build()
                        ),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        color = MaterialTheme.colors.primaryText,
                        text = response.data.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colors.primaryText,
                        text = response.data.symbol,
                        style = MaterialTheme.typography.body1,
                    )


                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "$" + response.data.price,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.secondaryText
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                color = MaterialTheme.colors.primaryText,
                                text = "1 Day",
                                style = MaterialTheme.typography.subtitle2
                            )

                            Text(
                                color = MaterialTheme.colors.primaryText,
                                text = "2 Days",
                                style = MaterialTheme.typography.subtitle2
                            )

                            Text(
                                color = MaterialTheme.colors.primaryText,
                                text = "3 Days",
                                style = MaterialTheme.typography.subtitle2
                            )
                        }

                        Column {
                            val percent1Day = response.data.percentDifference1Day()
                            val percent2Days = response.data.percentDifference2Days()
                            val percent3Days = response.data.percentDifference3Days()

                            // Function to get the appropriate arrow icon
                            fun getArrowIcon(percent: Double): Int {
                                return when {
                                    percent > 0 -> com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp
                                    percent < 0 -> com.google.android.material.R.drawable.material_ic_menu_arrow_down_black_24dp
                                    else -> 0 // No arrow for 0 percent change
                                }
                            }

                            // Function to get the appropriate color for the text
                            @Composable
                            fun getTextColor(percent: Double) =
                                if (percent > 0) Color.Green else MaterialTheme.colors.error

                            // Function to format the percentage
                            fun formatPercent(percent: Double) = "${percent}%"

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = getArrowIcon(percent1Day.toDouble())),
                                    contentDescription = "Arrow",
                                    tint = getTextColor(percent1Day.toDouble()),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    color = getTextColor(percent1Day.toDouble()),
                                    text = formatPercent(percent1Day.toDouble()),
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = getArrowIcon(percent2Days.toDouble())),
                                    contentDescription = "Arrow",
                                    tint = getTextColor(percent2Days.toDouble()),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    color = getTextColor(percent2Days.toDouble()),
                                    text = formatPercent(percent2Days.toDouble()),
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = getArrowIcon(percent3Days.toDouble())),
                                    contentDescription = "Arrow",
                                    tint = getTextColor(percent3Days.toDouble()),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    color = getTextColor(percent3Days.toDouble()),
                                    text = formatPercent(percent3Days.toDouble()),
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }

                        }
                    }
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = convertTimestampToDateTime(response.data.date),
                        color = MaterialTheme.colors.secondaryText
                    )
                    Text(
                        text = getTimeSinceLastAPICall(response.data.date),
                        color = MaterialTheme.colors.secondaryText
                    )
                }
            }


            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Check More On:",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
            )


            Row(modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = response.data.links[0].image)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(response.data.links[0].link)
                            launcher.launch(intent)
                        },
                    text = response.data.links[0].name,
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = response.data.links[1].image)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(response.data.links[1].link)
                            launcher.launch(intent)
                        },
                    text = response.data.links[1].name,
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = response.data.links[2].image)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(30.dp)
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(response.data.links[2].link)
                            launcher.launch(intent)
                        },
                    text = response.data.links[2].name,
                    fontSize = 20.sp
                )
            }
        }
    }
}

fun convertTimestampToDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun getTimeSinceLastAPICall(lastCallTimestamp: Long): String {
    val currentTimestamp = System.currentTimeMillis()
    val timeDifference = kotlin.math.abs(currentTimestamp - lastCallTimestamp)

    val seconds = timeDifference / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days ${if (days == 1L) "day" else "days"} ago"
        hours > 0 -> "$hours ${if (hours == 1L) "hour" else "hours"} ago"
        minutes > 0 -> "$minutes ${if (minutes == 1L) "minute" else "minutes"} ago"
        else -> "few seconds ago"
    }
}





