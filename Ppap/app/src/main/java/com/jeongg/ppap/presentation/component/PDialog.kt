package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.ui.theme.gray1
import com.jeongg.ppap.ui.theme.shapes

@Composable
fun PDialog(
    text: String = "정보컴퓨터공학부",
    onDeleteClick:() -> Unit = {},
    onEditClick:() -> Unit = {},
    onSubscribeClick:() -> Unit = {}
){
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(shapes.small)
                .background(Color.White)
                .padding(30.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MiniButton("삭제하기", onDeleteClick)
                MiniButton("수정하기", onEditClick)
            }
        }
        PButton(text = "구독하기", onClick = onSubscribeClick)
    }
}

@Composable
fun MiniButton(
    text: String = "",
    onClick: () -> Unit = {}
){
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier
            .clip(shapes.small)
            .border(1.dp, gray1, shapes.small)
            .clickable(onClick = onClick)
            .padding(30.dp, 10.dp),
        color = Color.Black
    )
}
