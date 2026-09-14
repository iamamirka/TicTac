package com.tictac.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tictac.R
import com.tictac.domain.model.Mark

/**
 * How big the X / O glyph inside a [TicTacBoardCell] is. Each step has its own
 * drawable rather than a scaled 48dp one, so the stroke stays legible as the
 * board gets denser.
 */
public enum class TicTacMarkSize {

    /** 16dp - dense boards, where a cell is barely bigger than the glyph. */
    S,

    /** 24dp - mid-density boards. */
    M,

    /** 48dp - the default, for a 3x3 board. */
    L,
}

internal val TicTacMarkSize.dimension: Dp
    get() = when (this) {
        TicTacMarkSize.S -> 16.dp
        TicTacMarkSize.M -> 24.dp
        TicTacMarkSize.L -> 48.dp
    }

@DrawableRes
internal fun TicTacMarkSize.iconFor(mark: Mark): Int = when (this) {
    TicTacMarkSize.S -> if (mark == Mark.X) R.drawable.ic_x_16 else R.drawable.ic_o_16
    TicTacMarkSize.M -> if (mark == Mark.X) R.drawable.ic_x_24 else R.drawable.ic_o_24
    TicTacMarkSize.L -> if (mark == Mark.X) R.drawable.ic_x_48 else R.drawable.ic_o_48
}
