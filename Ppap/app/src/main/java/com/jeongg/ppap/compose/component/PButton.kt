package com.jeongg.ppap.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.compose.theme.main_yellow

@Composable
fun PButton(
    modifier: Modifier = Modifier,
    color: Color = main_yellow,
    textColor: Color = Color.Black,
    text: String = "버튼",
    onClick: () -> Unit = {},
){
    Button(
        modifier = modifier.fillMaxWidth().height(44.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
        ),
        border = BorderStroke(1.5.dp, textColor),
        contentPadding = PaddingValues(0.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

