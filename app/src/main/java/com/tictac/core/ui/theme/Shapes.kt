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
    /** One cell of the 3x3 board. */
    val boardCell: CornerBasedShape = RoundedCornerShape(12.dp),
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
