package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.gray3
import com.jeongg.ppap.theme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PTextField(
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "힌트"
){
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 40.dp)
            .focusRequester(focusRequester),
        textStyle = typography.bodyMedium,
    ){
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = text,
            innerTextField = it,
            enabled = true,
            singleLine = false,
            interactionSource = NoRippleInteractionSource,
            visualTransformation = VisualTransformation.None,
            placeholder = { TextFieldPlaceHolder(placeholder) },
            contentPadding = PaddingValues(10.dp),
            container = { TextFieldOutlineBorder() }
        )
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldOutlineBorder() {
    TextFieldDefaults.OutlinedBorderContainerBox(
        enabled = true,
        isError = false,
        interactionSource = NoRippleInteractionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            placeholderColor = gray3,
            unfocusedBorderColor = gray3,
            focusedBorderColor = Color.Black,
        ),
        shape = MaterialTheme.shapes.small,
    )
}

@Composable
private fun TextFieldPlaceHolder(placeholder: String) {
    Text(
        text = placeholder,
        style = typography.bodyMedium,
        color = gray3,
    )
}