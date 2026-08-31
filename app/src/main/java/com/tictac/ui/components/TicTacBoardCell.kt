package com.tictac.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.tictac.core.ui.theme.TicTacTheme

/** One square of the 3x3 board. */
public enum class Mark { X, O }

@Composable
public fun TicTacBoardCell(
    mark: Mark?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isWinning: Boolean = false,
) {
    val colors = TicTacTheme.colors
    Box(
        modifier = modifier
            .size(88.dp)
            .clip(TicTacTheme.shapes.boardCell)
            .background(if (isWinning) colors.winHighlight else colors.boardCell)
            .border(
                width = 2.dp,
                color = colors.boardGrid,
                shape = TicTacTheme.shapes.boardCell,
            )
            .clickable(enabled = mark == null, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (mark != null) {
            Text(
                text = if (mark == Mark.X) "X" else "O",
                style = TicTacTheme.typography.mark,
                color = if (mark == Mark.X) colors.markX else colors.markO,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TicTacBoardCellPreview() {
    TicTacTheme {
        TicTacBoardCell(mark = Mark.X, onClick = {})
    }
}
