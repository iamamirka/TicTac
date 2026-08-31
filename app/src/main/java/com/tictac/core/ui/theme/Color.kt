package com.tictac.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Raw brand palette.
 *
 * Never reference these from UI code - always go through the semantic tokens in
 * [TicTacColors], otherwise light/dark switching stops working.
 */
internal object TicTacPalette {
    val Ink: Color = Color(0xFF05050D)
    val InkSoft: Color = Color(0xFF101019)
    val InkMuted: Color = Color(0xFF1B1B26)
    val OffWhite: Color = Color(0xFFF2F0EC)
    val Paper: Color = Color(0xFFFBF7F0)
    val PaperPure: Color = Color(0xFFFFFFFF)
    val PaperMuted: Color = Color(0xFFF1EAE0)
    val Beige: Color = Color(0xFF7E6F6F)
    val BeigeLight: Color = Color(0xFFA79B9B)
    val BeigeSand: Color = Color(0xFFC9BDB1)
    val Slate: Color = Color(0xFF5C5252)
    val Orange: Color = Color(0xFFE59106)
    val OrangeDeep: Color = Color(0xFFC97A04)
    val Lime: Color = Color(0xFFCBF3A0)
    val LimeDeep: Color = Color(0xFF4E8A1F)
    val Coral: Color = Color(0xFFFF6B5E)
    val CoralDeep: Color = Color(0xFFC0392B)
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

@Immutable
public object TicTacColorsDark : TicTacColors {
    override val background: Color = TicTacPalette.Ink
    override val surface: Color = TicTacPalette.InkSoft
    override val surfaceVariant: Color = TicTacPalette.InkMuted

    override val onBackground: Color = TicTacPalette.OffWhite
    override val onSurface: Color = TicTacPalette.OffWhite
    override val onSurfaceVariant: Color = TicTacPalette.BeigeLight
    override val outline: Color = TicTacPalette.Beige

    override val accent: Color = TicTacPalette.Orange
    override val onAccent: Color = TicTacPalette.Ink
    override val positive: Color = TicTacPalette.Lime
    override val negative: Color = TicTacPalette.Coral

    override val markX: Color = TicTacPalette.Orange
    override val markO: Color = TicTacPalette.Lime
    override val boardGrid: Color = TicTacPalette.Beige
    override val boardCell: Color = TicTacPalette.InkSoft
    override val winHighlight: Color = TicTacPalette.Lime
}

@Immutable
public object TicTacColorsLight : TicTacColors {
    override val background: Color = TicTacPalette.Paper
    override val surface: Color = TicTacPalette.PaperPure
    override val surfaceVariant: Color = TicTacPalette.PaperMuted

    override val onBackground: Color = TicTacPalette.Ink
    override val onSurface: Color = TicTacPalette.Ink
    override val onSurfaceVariant: Color = TicTacPalette.Slate
    override val outline: Color = TicTacPalette.BeigeSand

    override val accent: Color = TicTacPalette.OrangeDeep
    override val onAccent: Color = TicTacPalette.PaperPure
    override val positive: Color = TicTacPalette.LimeDeep
    override val negative: Color = TicTacPalette.CoralDeep

    override val markX: Color = TicTacPalette.OrangeDeep
    override val markO: Color = TicTacPalette.LimeDeep
    override val boardGrid: Color = TicTacPalette.BeigeSand
    override val boardCell: Color = TicTacPalette.PaperPure
    override val winHighlight: Color = TicTacPalette.Lime
}
