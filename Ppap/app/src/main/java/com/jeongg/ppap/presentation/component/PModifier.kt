package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource

fun Modifier.negativePadding(dp: Dp = 30.dp) = then (
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints.offset((dp * 2).roundToPx()))
        layout(
            placeable.width,
            placeable.height
        ) { placeable.place(0, 0) }
    }
)
fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}
fun Modifier.noRippleClickable(onClick: () -> Unit = {}): Modifier{
    return this.clickable(
        interactionSource = NoRippleInteractionSource,
        indication = null,
        onClick = onClick
    )
}