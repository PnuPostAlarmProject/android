package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.ui.theme.Dimens

@Composable
fun PTitle(
    modifier: Modifier = Modifier,
    title: String = "제목",
    description: String = "설명입니다.",
    content: @Composable (ColumnScope.() -> Unit),
){
    Column(
        modifier = modifier.padding(Dimens.PaddingNormal).fillMaxSize()
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        PDivider(modifier = Modifier.padding(top = 7.dp, bottom = 23.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.padding(top = Dimens.PaddingNormal))
        Column(content=content)
    }
}