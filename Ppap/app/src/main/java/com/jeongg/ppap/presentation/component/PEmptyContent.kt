package com.jeongg.ppap.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.ui.theme.gray2

@Composable
fun PEmptyContent(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int = R.drawable.pineapple_gray,
    content: String = stringResource(R.string.empty_scrap)
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Image(
            painter = painterResource(id),
            contentDescription = content,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = gray2,
            textAlign = TextAlign.Center
        )
    }
}