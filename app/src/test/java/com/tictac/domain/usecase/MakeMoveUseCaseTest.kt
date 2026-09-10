package com.tictac.domain.usecase

import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.Mark
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MakeMoveUseCaseTest {

    private val makeMove = MakeMoveUseCase()

    @Test
    fun `move on a free cell is applied`() {
        val board = makeMove(GameBoard(), index = 4, mark = Mark.X)
        assertEquals(Mark.X, board?.markAt(4))
    }

    @Test
    fun `move on a taken cell is rejected`() {
        val board = GameBoard().withMark(4, Mark.X)
        assertNull(makeMove(board, index = 4, mark = Mark.O))
    }

    @Test
    fun `move out of bounds is rejected`() {
        assertNull(makeMove(GameBoard(), index = 9, mark = Mark.X))
        assertNull(makeMove(GameBoard(), index = -1, mark = Mark.X))
    }

    @Test
    fun `original board is not mutated`() {
        val board = GameBoard()
        makeMove(board, index = 0, mark = Mark.X)
        assertNull(board.markAt(0))
    }
}
