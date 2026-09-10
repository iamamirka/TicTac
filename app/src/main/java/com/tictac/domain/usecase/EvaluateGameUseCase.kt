package com.tictac.domain.usecase

import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import javax.inject.Inject

/** Decides whether the board is won, drawn, or still in play. */
public class EvaluateGameUseCase @Inject constructor() {

    public operator fun invoke(board: GameBoard): GameResult {
        for (line in GameBoard.WINNING_LINES) {
            val first = board.markAt(line[0]) ?: continue
            if (line.all { board.markAt(it) == first }) {
                return GameResult.Win(mark = first, line = line)
            }
        }
        return if (board.isFull) GameResult.Draw else GameResult.InProgress
    }
}
