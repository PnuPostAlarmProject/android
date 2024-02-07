package com.jeongg.ppap.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.jeongg.ppap.data._const.AppTheme
import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data.util.PDataStore

private val darkColorScheme = darkColorScheme(
    background = Color.Black,
    surface = Color.White,
    outline = gray4,
    outlineVariant = gray3,
    primary = gray4,
    secondary = gray4
)

private val lightColorScheme = lightColorScheme(
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
    val dataStoreTheme = PDataStore(context).getData(DataStoreKey.PPAP_THEME_KEY.name).ifEmpty {
        AppTheme.DYNAMIC_THEME.name
    }
    val dynamicColorScheme = if (isSystemInDarkTheme()) darkColorScheme else lightColorScheme

    val colorScheme = when (AppTheme.valueOf(dataStoreTheme)) {
        AppTheme.DARK_THEME -> darkColorScheme
        AppTheme.LIGHT_THEME -> lightColorScheme
        AppTheme.DYNAMIC_THEME -> dynamicColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}