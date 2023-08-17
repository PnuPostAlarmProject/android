package com.jeongg.ppap.presentation.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.ui.theme.main_yellow
import com.jeongg.ppap.ui.theme.shapes

@Composable
fun PButton(
    modifier: Modifier = Modifier,
    @DrawableRes painter: Int = -1,
    color: Color = main_yellow,
    textColor: Color = Color.Black,
    text: String = "버튼",
    onClick: () -> Unit = {},
    shape: Shape = shapes.small
){
    Button(
        modifier = modifier.fillMaxWidth().height(44.dp),
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = textColor,
        ),
        border = if (painter != -1) null else BorderStroke(1.5.dp, textColor),
        contentPadding = PaddingValues(0.dp)
    ){
        if (painter != -1) {
            Image (
                painter = painterResource(painter),
                contentDescription = "edit",
                modifier = Modifier.padding(end = 5.dp).size(22.dp),
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

