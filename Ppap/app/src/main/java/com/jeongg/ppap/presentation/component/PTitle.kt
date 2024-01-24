package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jeongg.ppap.theme.Dimens

@Composable
fun PTitle(
    modifier: Modifier = Modifier,
    title: String = "제목",
    content: LazyListScope.() -> Unit,
){
    LazyColumn(
        modifier = modifier
            .padding(Dimens.ScreenPadding)
            .fillMaxSize()
    ){
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        content()
    }
}