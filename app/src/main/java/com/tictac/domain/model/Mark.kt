package com.tictac.domain.model

/** A player's mark. */
public enum class Mark {
    X,
    O,
    ;

    public fun opponent(): Mark = if (this == X) O else X
}
