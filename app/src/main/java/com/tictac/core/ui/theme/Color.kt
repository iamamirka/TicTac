package com.tictac.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Raw brand palette, sampled from the app mockup.
 *
 * Never reference these from UI code - always go through the semantic tokens in
 * [TicTacColors], otherwise light/dark switching stops working.
 */
internal object TicTacPalette {
    /* Dark ramp - near-black with a faint cool tint */
    val Charcoal: Color = Color(0xFF121414)
    val Graphite: Color = Color(0xFF1F2121)
    val Gunmetal: Color = Color(0xFF262929)
    val Steel: Color = Color(0xFF252929)

    /* Light ramp */
    val Cloud: Color = Color(0xFFF4F6F6)
    val CloudPure: Color = Color(0xFFFFFFFF)
    val CloudMuted: Color = Color(0xFFE6EAEA)
    val CloudLine: Color = Color(0xFFC9D0D0)

    /* Content - MistDim is a touch lighter than the mockup so secondary
     * text still clears 4.5:1 on the elevated Gunmetal surfaces. */
    val Mist: Color = Color(0xFFCFDFE0)
    val MistDim: Color = Color(0xFF8D9797)
    val SlateDim: Color = Color(0xFF5A6363)

    /* Brand green - the primary action */
    val Green: Color = Color(0xFF4B9760)
    val GreenDeep: Color = Color(0xFF3A7F4E)

    /* Status */
    val Red: Color = Color(0xFFD2564C)
    val RedDeep: Color = Color(0xFFB4443A)
}

/**
 * Semantic color tokens. Read them through `TicTacTheme.colors`.
 */
@Immutable
public interface TicTacColors {

    /* Surfaces */
    public val background: Color
    public val surface: Color
    public val surfaceVariant: Color

    /* Content */
    public val onBackground: Color
    public val onSurface: Color
    public val onSurfaceVariant: Color
    public val outline: Color

    /* Accents and status */
    public val accent: Color
    public val onAccent: Color
    public val positive: Color
    public val negative: Color

    /* Game */
    public val markX: Color
    public val markO: Color
    public val boardGrid: Color
    public val boardCell: Color
    public val winHighlight: Color

    public companion object {
        public fun provideDefaultColors(isDarkTheme: Boolean): TicTacColors =
            if (isDarkTheme) TicTacColorsDark else TicTacColorsLight
    }
}

/** The mockup palette: near-black ground, mist content, green action. */
@Immutable
public object TicTacColorsDark : TicTacColors {
    override val background: Color = TicTacPalette.Charcoal
    override val surface: Color = TicTacPalette.Graphite
    override val surfaceVariant: Color = TicTacPalette.Gunmetal

    override val onBackground: Color = TicTacPalette.Mist
    override val onSurface: Color = TicTacPalette.Mist
    override val onSurfaceVariant: Color = TicTacPalette.MistDim
    override val outline: Color = TicTacPalette.Steel

    override val accent: Color = TicTacPalette.Green
    override val onAccent: Color = TicTacPalette.CloudPure
    override val positive: Color = TicTacPalette.Green
    override val negative: Color = TicTacPalette.Red

    override val markX: Color = TicTacPalette.Mist
    override val markO: Color = TicTacPalette.Green
    override val boardGrid: Color = TicTacPalette.Steel
    override val boardCell: Color = TicTacPalette.Gunmetal
    override val winHighlight: Color = TicTacPalette.Green
}

/** Same roles inverted, with the green darkened to keep white legible on it. */
@Immutable
public object TicTacColorsLight : TicTacColors {
    override val background: Color = TicTacPalette.Cloud
    override val surface: Color = TicTacPalette.CloudPure
    override val surfaceVariant: Color = TicTacPalette.CloudMuted

    override val onBackground: Color = TicTacPalette.Charcoal
    override val onSurface: Color = TicTacPalette.Charcoal
    override val onSurfaceVariant: Color = TicTacPalette.SlateDim
    override val outline: Color = TicTacPalette.CloudLine

    override val accent: Color = TicTacPalette.GreenDeep
    override val onAccent: Color = TicTacPalette.CloudPure
    override val positive: Color = TicTacPalette.GreenDeep
    override val negative: Color = TicTacPalette.RedDeep

    override val markX: Color = TicTacPalette.Charcoal
    override val markO: Color = TicTacPalette.GreenDeep
    override val boardGrid: Color = TicTacPalette.CloudLine
    override val boardCell: Color = TicTacPalette.CloudPure
    override val winHighlight: Color = TicTacPalette.GreenDeep
}
