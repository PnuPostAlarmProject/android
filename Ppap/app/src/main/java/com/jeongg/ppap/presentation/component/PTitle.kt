package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.ui.theme.Dimens
import com.jeongg.ppap.ui.theme.gray3

@Composable
fun PTitle(
    modifier: Modifier = Modifier,
    title: String = "제목",
    description: String = "",
    onUpPress:() -> Unit = {},
    content: @Composable (ColumnScope.() -> Unit),
){
    Column(
        modifier = modifier.padding(Dimens.ScreenPadding).fillMaxSize()
    ){
        Box{
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "navigate to back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 10.dp)
                    .size(30.dp)
                    .rotate(180f)
                    .clickable(onClick = onUpPress),
                colorFilter = ColorFilter.tint(gray3)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        PDivider(modifier = Modifier.padding(top = 7.dp))
        if (description.isNotBlank()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 23.dp, bottom = Dimens.PaddingSmall)
            )
        }
        Column(content=content)
    }
}