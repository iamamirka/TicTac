package com.tictac.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * The app theme. Provides the TicTac design tokens and keeps the Material 3
 * theme in sync with them, so stock M3 widgets are on-brand too.
 *
 * Dynamic (wallpaper) color is deliberately not used - the X/O palette is the brand.
 */
@Composable
public fun TicTacTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: TicTacColors = TicTacColors.provideDefaultColors(darkTheme),
    typography: TicTacTypography = TicTacTypography(),
    shapes: TicTacShapes = TicTacShapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTicTacColors provides colors,
        LocalTicTacTypography provides typography,
        LocalTicTacShapes provides shapes,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterialColorScheme(darkTheme),
            typography = typography.toMaterialTypography(),
            shapes = shapes.toMaterialShapes(),
            content = content,
        )
    }
}

/**
 * Token accessor: `TicTacTheme.colors`, `TicTacTheme.typography`, `TicTacTheme.shapes`.
 */
public object TicTacTheme {

    public val colors: TicTacColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTicTacColors.current

    public val typography: TicTacTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTicTacTypography.current

    public val shapes: TicTacShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalTicTacShapes.current
}

private val LocalTicTacColors = staticCompositionLocalOf<TicTacColors> {
    error("No TicTacColors provided. Wrap your content in TicTacTheme { }.")
}

private val LocalTicTacTypography = staticCompositionLocalOf { TicTacTypography() }

private val LocalTicTacShapes = staticCompositionLocalOf { TicTacShapes() }

private fun TicTacColors.toMaterialColorScheme(darkTheme: Boolean): ColorScheme =
    if (darkTheme) {
        darkColorScheme(
            primary = accent,
            onPrimary = onAccent,
            secondary = markO,
            onSecondary = background,
            tertiary = positive,
            onTertiary = background,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = negative,
            onError = background,
        )
    } else {
        lightColorScheme(
            primary = accent,
            onPrimary = onAccent,
            secondary = markO,
            onSecondary = surface,
            tertiary = positive,
            onTertiary = surface,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant,
            outline = outline,
            error = negative,
            onError = surface,
        )
    }
