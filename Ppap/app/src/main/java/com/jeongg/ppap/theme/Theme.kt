package com.jeongg.ppap.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.jeongg.ppap.data.util.DARK_THEME
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.PPAP_THEME_KEY

private val DarkColorScheme = darkColorScheme(
    background = Color.Black,
    surface = Color.White,
    outline = gray4,
    outlineVariant = gray3,
    primary = gray4,
    secondary = gray4
)

private val LightColorScheme = lightColorScheme(
    background = Color.White,
    surface = Color.Black,
    outline = gray1,
    outlineVariant = gray4,
    primary = gray2,
    secondary = gray2
)

@Composable
fun PpapTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val dataStoreTheme = PDataStore(context).getData(PPAP_THEME_KEY)
    val theme = if (dataStoreTheme == "") isSystemInDarkTheme()
                else (dataStoreTheme == DARK_THEME)
    val colorScheme = if (theme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}