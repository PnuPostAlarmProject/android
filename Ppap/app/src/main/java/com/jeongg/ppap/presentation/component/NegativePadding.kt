package com.jeongg.ppap.presentation.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset

fun Modifier.negativePadding(dp: Dp = 30.dp) = then (
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints.offset((dp * 2).roundToPx()))
        layout(
            placeable.width,
            placeable.height
        ) { placeable.place(0, 0) }
    }
)