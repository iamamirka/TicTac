package com.tictac.ui.components

/**
 * How much room a [TicTacButton] takes up. Drives padding, text style and icon
 * size together, so a button never ends up with big text in a tight pill.
 */
public enum class TicTacButtonSize {

    /** Compact - for chips and toolbar actions sitting next to other content. */
    S,

    /** The default - inline actions inside a screen. */
    M,

    /** Roomy - a screen's main call to action. */
    L,
}
