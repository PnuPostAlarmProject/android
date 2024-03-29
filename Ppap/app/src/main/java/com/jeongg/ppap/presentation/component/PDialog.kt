package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray1

@Composable
fun PDialog(
    text: String = "",
    isOpen: MutableState<Boolean>,
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
){
    if(isOpen.value.not()) return
    Dialog(
        onDismissRequest = { isOpen.value = false }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .clip(MaterialTheme.shapes.small)
                .background(Color.White)
                .padding(Dimens.PaddingSmall),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            DialogButton(isOpen, onConfirmClick, onCancelClick)
        }
    }
}

@Composable
private fun DialogButton(
    isOpen: MutableState<Boolean> = mutableStateOf(false),
    onConfirmClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        MiniButton(
            modifier = Modifier.weight(1f),
            text = "아니요",
            onClick = {
                isOpen.value = false
                onCancelClick()
            }
        )
        MiniButton(
            modifier = Modifier.weight(1f),
            text = "예",
            onClick = {
                isOpen.value = false
                onConfirmClick()
            }
        )
    }
}

@Composable
private fun MiniButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {}
){
    Text(
        text = text,
        color = Color.Black,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .border(1.dp, gray1, MaterialTheme.shapes.extraSmall)
            .noRippleClickable(onClick = onClick)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    )
}
