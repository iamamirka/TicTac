package com.tictac.ui.screens.game

import com.tictac.core.mvi.MviViewModel
import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark
import com.tictac.domain.usecase.EvaluateGameUseCase
import com.tictac.domain.usecase.MakeMoveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class GameViewModel @Inject constructor(
    private val makeMove: MakeMoveUseCase,
    private val evaluateGame: EvaluateGameUseCase,
) : MviViewModel<GameUiState, GameIntent, GameEffect>(GameUiState()) {

    override fun onIntent(intent: GameIntent) {
        when (intent) {
            is GameIntent.CellClicked -> onCellClicked(intent.index)
            is GameIntent.BoardSizeSelected -> onBoardSizeSelected(intent.size)
            GameIntent.NewGameClicked -> onNewGame()
            GameIntent.ResetScoreClicked -> updateState { copy(scoreX = 0, scoreO = 0) }
        }
    }

    /** Idempotent, so re-entering the screen does not wipe a round of the same size. */
    private fun onBoardSizeSelected(size: Int) {
        if (state.value.board.size == size) return
        updateState {
            copy(
                board = GameBoard.empty(size),
                currentTurn = Mark.X,
                result = GameResult.InProgress,
            )
        }
    }

    private fun onCellClicked(index: Int) {
        val current = state.value
        if (current.isFinished) {
            handleEffect(GameEffect.ShowMessage("Round is over - start a new game"))
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
            is GameResult.Win -> handleEffect(GameEffect.ShowMessage("${result.mark.name} wins!"))
            GameResult.Draw -> handleEffect(GameEffect.ShowMessage("Draw"))
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
                board = GameBoard.empty(board.size),
                currentTurn = opener,
                result = GameResult.InProgress,
            )
        }
    }
}
