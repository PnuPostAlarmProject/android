package com.jeongg.ppap.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.theme.gray3

@Composable
fun PEmptyContent(
    modifier: Modifier = Modifier,
    @DrawableRes id: Int = R.drawable.pineapple_gray,
    message: String = stringResource(R.string.empty_subscribes),
    buttonText: String = "구독 추가하기",
    onClick: () -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize().testTag("empty_content"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ){
        Image(
            painter = painterResource(id),
            contentDescription = message,
            modifier = Modifier.width(130.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = gray3,
            textAlign = TextAlign.Center
        )
        PButton(
            text = buttonText,
            horizontalPadding = 45.dp,
            verticalPadding = 3.dp,
            onClick = onClick
        )
    }
}