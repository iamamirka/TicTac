package com.tictac.ui.components

/**
 * How much visual weight a [TicTacButton] carries. One screen should show at most
 * one [Primary] button, so the main action stays obvious.
 */
public enum class TicTacButtonMode {

    /** Filled with the brand accent - the main action. */
    Primary,

    /** Filled with a muted surface - a supporting action. */
    Secondary,

    /** Outline only - a low-weight action such as cancel or back. */
    Tertiary,
}
