package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray3

@Composable
fun PTitle(
    modifier: Modifier = Modifier,
    title: String = "제목",
    content: @Composable (ColumnScope.() -> Unit),
){
    Column(
        modifier = modifier
            .padding(Dimens.ScreenPadding)
            .fillMaxSize()
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        content()
    }
}