package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.theme.gray1

@Composable
fun PDialog(
    text: String = "정보컴퓨터공학부",
    onDeleteClick:() -> Unit = {},
    onEditClick:() -> Unit = {},
    onSubscribeClick:() -> Unit = {}
){
    Column(
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(MaterialTheme.shapes.small)
                .background(Color.White)
                .padding(Dimens.PaddingNormal)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                MiniButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.delete_button),
                    onClick = onDeleteClick
                )
                MiniButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.edit_button),
                    onClick = onEditClick
                )
            }
        }
        PButton(text = stringResource(R.string.subscribe_button), onClick = onSubscribeClick)
    }
}

@Composable
fun MiniButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {}
){
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, gray1, MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}
