package com.darkfetchvip.tr.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Dark = darkColorScheme(
    primary = Color(0xFF5EDCFF),
    secondary = Color(0xFF9D7BFF),
    background = Color(0xFF090909),
    surface = Color(0xFF141520)
)

@Composable
fun DarkFetchTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) Dark else lightColorScheme(), content = content)
}
