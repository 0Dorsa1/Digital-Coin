package com.example.digitalcoin.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val darkColorPalette = darkColors(
    primary = Green000,
    primaryVariant = Black001,
    background = DarkBlue000,
)

private val lightColorPalette = lightColors(
    primary = Green000,
    primaryVariant = Black001,
    background = DarkBlue000,
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val colors = if (darkTheme) {
            darkColorPalette
        } else {
            lightColorPalette
        }

        CompositionLocalProvider {
            MaterialTheme(
                colors = colors,
                content = content
            )
        }
    }
}
