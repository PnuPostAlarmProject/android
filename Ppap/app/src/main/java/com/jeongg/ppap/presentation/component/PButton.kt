package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.bright_yellow

@Composable
fun PButton(
    modifier: Modifier = Modifier,
    color: Color = bright_yellow,
    textColor: Color = Color.Black,
    text: String = "",
    onClick: () -> Unit = {},
    horizontalPadding: Dp = 60.dp,
    verticalPadding: Dp = 13.dp
){
    Button(
        modifier = modifier.wrapContentSize(),
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
        ),
        contentPadding = PaddingValues(horizontalPadding, verticalPadding),
        interactionSource = NoRippleInteractionSource
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}

