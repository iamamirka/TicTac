package com.tictac.domain.usecase

import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.Mark
import javax.inject.Inject

/** Places [mark] on a free cell. Returns null when the move is not legal. */
public class MakeMoveUseCase @Inject constructor() {

    public operator fun invoke(board: GameBoard, index: Int, mark: Mark): GameBoard? {
        if (index !in 0 until board.cellCount) return null
        if (!board.isFreeAt(index)) return null
        return board.withMark(index, mark)
    }
}
