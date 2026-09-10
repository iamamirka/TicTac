package com.tictac.domain.usecase

import com.tictac.domain.model.GameBoard
import com.tictac.domain.model.GameResult
import com.tictac.domain.model.Mark
import org.junit.Assert.assertEquals
import org.junit.Test

class EvaluateGameUseCaseTest {

    private val evaluate = EvaluateGameUseCase()

    private fun boardOf(pattern: String): GameBoard {
        require(pattern.length == GameBoard.CELL_COUNT) { "pattern must be 9 chars" }
        return GameBoard(
            pattern.map { char ->
                when (char) {
                    'X' -> Mark.X
                    'O' -> Mark.O
                    else -> null
                }
            },
        )
    }

    @Test
    fun `empty board is in progress`() {
        assertEquals(GameResult.InProgress, evaluate(boardOf("---------")))
    }

    @Test
    fun `partial board is in progress`() {
        assertEquals(GameResult.InProgress, evaluate(boardOf("XO-X-----")))
    }

    @Test
    fun `top row wins`() {
        assertEquals(GameResult.Win(Mark.X, listOf(0, 1, 2)), evaluate(boardOf("XXXOO----")))
    }

    @Test
    fun `middle row wins`() {
        assertEquals(GameResult.Win(Mark.O, listOf(3, 4, 5)), evaluate(boardOf("X--OOOX--")))
    }

    @Test
    fun `bottom row wins`() {
        assertEquals(GameResult.Win(Mark.X, listOf(6, 7, 8)), evaluate(boardOf("OO-O--XXX")))
    }

    @Test
    fun `left column wins`() {
        assertEquals(GameResult.Win(Mark.X, listOf(0, 3, 6)), evaluate(boardOf("XO-XO-X--")))
    }

    @Test
    fun `middle column wins`() {
        assertEquals(GameResult.Win(Mark.O, listOf(1, 4, 7)), evaluate(boardOf("XO-XO--O-")))
    }

    @Test
    fun `right column wins`() {
        assertEquals(GameResult.Win(Mark.X, listOf(2, 5, 8)), evaluate(boardOf("OOX--X--X")))
    }

    @Test
    fun `main diagonal wins`() {
        assertEquals(GameResult.Win(Mark.X, listOf(0, 4, 8)), evaluate(boardOf("XO-OX---X")))
    }

    @Test
    fun `anti diagonal wins`() {
        assertEquals(GameResult.Win(Mark.O, listOf(2, 4, 6)), evaluate(boardOf("X-O-O-OXX")))
    }

    @Test
    fun `full board without a line is a draw`() {
        assertEquals(GameResult.Draw, evaluate(boardOf("XXOOOXXOX")))
    }
}
