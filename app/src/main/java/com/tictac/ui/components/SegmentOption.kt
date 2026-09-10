package com.tictac.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

/**
 * Something [TicTacSegmentedControl] can draw as one segment. Each option owns its
 * own presentation, so the control needs no label or icon lambdas from the caller.
 */
public interface SegmentOption {

    /** Label shown inside the segment. */
    public fun text(): String

    /** Icon shown after the label, or `null` for a text-only segment. */
    @Composable
    public fun icon(): Painter? = null
}
