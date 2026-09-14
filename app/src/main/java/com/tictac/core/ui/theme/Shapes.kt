package com.tictac.core.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

/**
 * Corner tokens. Read them through `TicTacTheme.shapes`.
 */
@Immutable
public data class TicTacShapes(
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(16.dp),
    val large: CornerBasedShape = RoundedCornerShape(24.dp),
    val card: CornerBasedShape = RoundedCornerShape(20.dp),
    /* A cell's radius has to shrink with the cell: 20dp on a 3x3 board reads as a
     * rounded square, but on a 12x12 board it would round the cell away entirely. */
    /** A cell on a sparse board (3x3). */
    val boardCellLarge: CornerBasedShape = RoundedCornerShape(20.dp),
    /** A cell on a mid-density board (up to 6x6). */
    val boardCellMedium: CornerBasedShape = RoundedCornerShape(12.dp),
    /** A cell on a dense board (7x7 and up). */
    val boardCellSmall: CornerBasedShape = RoundedCornerShape(6.dp),
    /** Pill button. */
    val button: CornerBasedShape = RoundedCornerShape(percent = 50),
)

/** Maps the corner tokens onto the Material 3 shape scale. */
internal fun TicTacShapes.toMaterialShapes(): Shapes = Shapes(
    extraSmall = small,
    small = small,
    medium = medium,
    large = large,
    extraLarge = card,
)
