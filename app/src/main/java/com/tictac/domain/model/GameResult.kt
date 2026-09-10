package com.tictac.domain.model

/** Outcome of the position currently on the board. */
public sealed interface GameResult {

    public data object InProgress : GameResult

    public data class Win(val mark: Mark, val line: List<Int>) : GameResult

    public data object Draw : GameResult
}
