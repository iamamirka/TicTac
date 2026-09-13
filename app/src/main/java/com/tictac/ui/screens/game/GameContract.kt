package com.tictac.ui.screens.game

import androidx.compose.runtime.Immutable
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark

@Immutable
internal data class GameUiState(
    val board: GameBoard = GameBoard(),
    val currentTurn: Mark = Mark.X,
    val result: GameResult = GameResult.InProgress,
    val scoreX: Int = 0,
    val scoreO: Int = 0,
) {
    val isFinished: Boolean get() = result != GameResult.InProgress

    val winningLine: List<Int> get() = (result as? GameResult.Win)?.line.orEmpty()
}

internal sealed interface GameIntent {
    data class CellClicked(val index: Int) : GameIntent

    /** Sent when the screen opens; starts a fresh board unless one of that size is already in play. */
    data class BoardSizeSelected(val size: Int) : GameIntent
    data object NewGameClicked : GameIntent
    data object ResetScoreClicked : GameIntent
}

internal sealed interface GameEffect {
    data class ShowMessage(val text: String) : GameEffect
}
