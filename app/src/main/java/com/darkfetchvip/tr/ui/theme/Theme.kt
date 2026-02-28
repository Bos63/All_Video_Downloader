package com.darkfetchvip.tr.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF59F3FF),
    secondary = Color(0xFF8A7CFF),
    background = Color(0xFF000000),
    surface = Color(0xFF111318)
)

private val LightScheme = lightColorScheme()

@Composable
fun DarkFetchTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) DarkScheme else LightScheme,
        content = content
    )
}
