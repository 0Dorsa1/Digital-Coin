package com.example.digitalcoin.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalcoin.utils.getActivity

@Preview
@Composable
private fun Preview(){
    ToolBar(title = "exapmle")
}

@Composable
fun ToolBar(modifier: Modifier = Modifier, title: String) {
    val activity = LocalContext.current.getActivity()

    Row(
        modifier = modifier
            .padding(end =16.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .rotate(180f)
                .size(60.dp)
                .clickable { activity?.onBackPressedDispatcher?.onBackPressed() }
                .padding(10.dp),
            painter = painterResource(id = androidx.appcompat.R.drawable.abc_ic_arrow_drop_right_black_24dp),
            contentDescription = "arrow",
            tint = Color.White
        )

        Text(
            modifier = Modifier.weight(1f), //fills all the available space
            text = title,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}