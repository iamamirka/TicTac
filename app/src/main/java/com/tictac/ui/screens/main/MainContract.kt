package com.tictac.ui.screens.main

import androidx.compose.runtime.Immutable
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark

@Immutable
internal data class MainUiState(
    val board: GameBoard = GameBoard(),
    val currentTurn: Mark = Mark.X,
    val result: GameResult = GameResult.InProgress,
    val scoreX: Int = 0,
    val scoreO: Int = 0,
) {
    val isFinished: Boolean get() = result != GameResult.InProgress

    val winningLine: List<Int> get() = (result as? GameResult.Win)?.line.orEmpty()
}

internal sealed interface MainIntent {
    data class CellClicked(val index: Int) : MainIntent
    data object NewGameClicked : MainIntent
    data object ResetScoreClicked : MainIntent
}

internal sealed interface MainEffect {
    data class ShowMessage(val text: String) : MainEffect
}
