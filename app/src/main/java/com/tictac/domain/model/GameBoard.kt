package com.tictac.domain.model

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.sqrt

/**
 * Immutable square board, row-major: index = row * [size] + column.
 * [size] is derived from the cell count, so a board is always square.
 */
public data class GameBoard(
    val cells: List<Mark?> = List(DEFAULT_SIZE * DEFAULT_SIZE) { null },
) {
    val size: Int = squareSideOf(cells.size)

    public val cellCount: Int get() = cells.size

    /** Marks in a row needed to win: the full line on 3x3, [WIN_LENGTH_CAP] on bigger boards. */
    public val winLength: Int get() = minOf(size, WIN_LENGTH_CAP)

    /** Every index run of [winLength] that wins on a board this size. */
    public val winningLines: List<List<Int>> get() = winningLinesFor(size)

    public val isFull: Boolean get() = cells.none { it == null }

    public fun markAt(index: Int): Mark? = cells[index]

    public fun isFreeAt(index: Int): Boolean = cells[index] == null

    public fun withMark(index: Int, mark: Mark): GameBoard =
        GameBoard(cells.toMutableList().also { it[index] = mark })

    public companion object {
        public const val DEFAULT_SIZE: Int = 3

        /** Caps the win condition so 9x9 and 12x12 stay winnable instead of needing a full line. */
        public const val WIN_LENGTH_CAP: Int = 5

        public fun empty(size: Int): GameBoard = GameBoard(List(size * size) { null })

        /** Lines only depend on the size, so they are built once per size and reused. */
        private val winningLinesCache = ConcurrentHashMap<Int, List<List<Int>>>()

        private fun winningLinesFor(size: Int): List<List<Int>> =
            winningLinesCache.getOrPut(size) { buildWinningLines(size) }

        /** Row, column, diagonal and anti-diagonal runs that fit inside the board. */
        private fun buildWinningLines(size: Int): List<List<Int>> {
            val length = minOf(size, WIN_LENGTH_CAP)
            val directions = listOf(0 to 1, 1 to 0, 1 to 1, 1 to -1)
            val lines = mutableListOf<List<Int>>()
            for (row in 0 until size) {
                for (column in 0 until size) {
                    for ((rowStep, columnStep) in directions) {
                        val endRow = row + rowStep * (length - 1)
                        val endColumn = column + columnStep * (length - 1)
                        if (endRow !in 0 until size || endColumn !in 0 until size) continue
                        lines += List(length) { step ->
                            (row + rowStep * step) * size + (column + columnStep * step)
                        }
                    }
                }
            }
            return lines
        }

        private fun squareSideOf(cellCount: Int): Int {
            val side = sqrt(cellCount.toDouble()).toInt()
            require(side > 0 && side * side == cellCount) {
                "A board must be square, got $cellCount cells"
            }
            return side
        }
    }
}
