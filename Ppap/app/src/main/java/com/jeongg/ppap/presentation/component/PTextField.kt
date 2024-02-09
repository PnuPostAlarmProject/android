package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.util.addFocusCleaner
import com.jeongg.ppap.presentation.util.NoRippleInteractionSource
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray3
import com.jeongg.ppap.theme.main_yellow
import com.jeongg.ppap.theme.typography

@Composable
fun PTextFieldWithCharacter(
    screenTitle: String = "",
    title: String = "구독 이름",
    text: String = "",
    onValueChange: (String) -> Unit = {},
    buttonText: String = "",
    onButtonClick: () -> Unit = {},
    placeholder: String = "",
    minHeight: Dp = 40.dp,
){
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .scrollable(scrollState, Orientation.Vertical)
            .addFocusCleaner(focusManager)
            .padding(Dimens.ScreenPadding),
    ) {
        Text(
            text = screenTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
        ) {
            PPAPCharacters()
            PTextFieldCard(
                title = title,
                text = text,
                onValueChange = { onValueChange(it) },
                placeholder = placeholder,
                minHeight = minHeight,
            )
            PButton(
                text = buttonText,
                onClick = onButtonClick
            )
        }
    }
}

@Composable
fun PTextFieldCard(
    modifier: Modifier = Modifier,
    title: String = "",
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    minHeight: Dp = 40.dp
){
    Column(
        modifier = modifier
    ) {
        PTextFieldTitle(
            modifier = Modifier.padding(top = 15.dp, bottom = 7.dp),
            title = title
        )
        PTextField(
            text = text,
            onValueChange = onValueChange,
            placeholder = placeholder,
            minHeight = minHeight
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PTextField(
    text: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "힌트",
    minHeight: Dp = 40.dp
){
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight)
            .focusRequester(focusRequester),
        textStyle = typography.bodyMedium,
    ){
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = it,
            enabled = true,
            singleLine = false,
            visualTransformation = VisualTransformation.None,
            interactionSource = NoRippleInteractionSource,
            placeholder = {
                TextFieldPlaceHolder(placeholder)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            contentPadding = PaddingValues(10.dp),
            container = { TextFieldOutlineBorder() },
        )
    }

}

@Composable
private fun PTextFieldTitle(
    modifier: Modifier = Modifier,
    title: String = ""
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = typography.displayLarge
        )
        Text(
            text = "*",
            style = typography.displayLarge,
            color = Color.Red
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFieldOutlineBorder() {
    OutlinedTextFieldDefaults.ContainerBox(
        enabled = true,
        isError = false,
        interactionSource = NoRippleInteractionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color.Black,
            containerColor = Color.White,
            focusedPlaceholderColor = gray3,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = gray3,
            cursorColor = main_yellow
        ),
        shape = MaterialTheme.shapes.small
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

@Composable
private fun PPAPCharacters() {
    Row(
        modifier = Modifier.size(200.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.pineapple),
            contentDescription = stringResource(R.string.characters),
        )
        Image(
            painter = painterResource(id = R.drawable.apple),
            contentDescription = stringResource(R.string.characters),
        )
    }
}