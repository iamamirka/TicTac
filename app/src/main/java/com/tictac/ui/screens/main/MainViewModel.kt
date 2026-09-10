package com.tictac.ui.screens.main

import com.tictac.core.mvi.MviViewModel
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark
import com.tictac.domain.usecase.EvaluateGameUseCase
import com.tictac.domain.usecase.MakeMoveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val makeMove: MakeMoveUseCase,
    private val evaluateGame: EvaluateGameUseCase,
) : MviViewModel<MainUiState, MainIntent, MainEffect>(MainUiState()) {

    override fun onIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.CellClicked -> onCellClicked(intent.index)
            MainIntent.NewGameClicked -> onNewGame()
            MainIntent.ResetScoreClicked -> updateState { copy(scoreX = 0, scoreO = 0) }
        }
    }

    private fun onCellClicked(index: Int) {
        val current = state.value
        if (current.isFinished) {
            handleEffect(MainEffect.ShowMessage("Round is over - start a new game"))
            return
        }
        val board = makeMove(current.board, index, current.currentTurn) ?: return
        val result = evaluateGame(board)

        updateState {
            copy(
                board = board,
                result = result,
                currentTurn = if (result == GameResult.InProgress) currentTurn.opponent() else currentTurn,
                scoreX = scoreX + if (result is GameResult.Win && result.mark == Mark.X) 1 else 0,
                scoreO = scoreO + if (result is GameResult.Win && result.mark == Mark.O) 1 else 0,
            )
        }

        when (result) {
            is GameResult.Win -> handleEffect(MainEffect.ShowMessage("${result.mark.name} wins!"))
            GameResult.Draw -> handleEffect(MainEffect.ShowMessage("Draw"))
            GameResult.InProgress -> Unit
        }
    }

    /** Loser of the finished round opens the next one; X opens after a draw. */
    private fun onNewGame() {
        val previous = state.value.result
        val opener = when (previous) {
            is GameResult.Win -> previous.mark.opponent()
            else -> Mark.X
        }
        updateState {
            copy(
                board = GameBoard(),
                currentTurn = opener,
                result = GameResult.InProgress,
            )
        }
    }
}
