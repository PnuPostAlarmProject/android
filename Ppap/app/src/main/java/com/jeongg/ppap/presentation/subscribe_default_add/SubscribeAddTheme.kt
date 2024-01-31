package com.jeongg.ppap.presentation.subscribe_default_add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.noRippleClickable
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray5
import com.jeongg.ppap.theme.gray6
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SubscribeAddCardTheme(
    eventFlow: SharedFlow<PEvent>,
    text: String = "전체",
    onNavigate: () -> Unit = {},
    contents: @Composable ColumnScope.() -> Unit,
){
    LaunchedEffectEvent(
        eventFlow = eventFlow,
        onNavigate = onNavigate
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = Dimens.ScreenPadding,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { SubscribeDefaultAddTitle() }
        item { SubscribeDefaultAddSubTitle(text) }
        item {
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), MaterialTheme.shapes.large)
                    .padding(20.dp),
                content = contents
            )
        }
    }
}

@Composable
fun NoticeBoardItem(
    onClick: () -> Unit = {},
    text: String = ""
){
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .noRippleClickable(onClick),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 43.dp),
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = "navigation arrow",
            tint = gray6,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(30.dp)
        )
    }
}

@Composable
private fun SubscribeDefaultAddTitle() {
    Text(
        text = "구독 추가",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun SubscribeDefaultAddSubTitle(
    text: String = ""
) {
    Text(
        text = text,
        color = gray5,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
    )
}