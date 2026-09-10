package com.tictac.domain.model

/**
 * Immutable 3x3 board, row-major: index = row * SIZE + column.
 */
public data class GameBoard(
    val cells: List<Mark?> = List(CELL_COUNT) { null },
) {
    init {
        require(cells.size == CELL_COUNT) { "A board has $CELL_COUNT cells, got ${cells.size}" }
    }

    public val isFull: Boolean get() = cells.none { it == null }

    public fun markAt(index: Int): Mark? = cells[index]

    public fun isFreeAt(index: Int): Boolean = cells[index] == null

    public fun withMark(index: Int, mark: Mark): GameBoard =
        GameBoard(cells.toMutableList().also { it[index] = mark })

    public companion object {
        public const val SIZE: Int = 3
        public const val CELL_COUNT: Int = SIZE * SIZE

        /** Every index triple that wins: 3 rows, 3 columns, 2 diagonals. */
        public val WINNING_LINES: List<List<Int>> = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6),
        )
    }
}
