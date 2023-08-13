package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.ui.theme.gray1

@Composable
fun PDivider(
    modifier: Modifier = Modifier
){
    Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = gray1
    )
}